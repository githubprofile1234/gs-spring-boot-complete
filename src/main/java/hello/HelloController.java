package hello;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
	
	private static List<String> al = null;
    
    @RequestMapping("/connected")
    public String index(@RequestParam(value="origin", defaultValue="") String origin, @RequestParam(value="destination", defaultValue="") String destination) {	
		String output = "no";
		if (!origin.isEmpty() && !destination.isEmpty()) {			
			if (al == null) {
		    	String fileName = "c://city.txt";
		
				al = new ArrayList<String>();
				try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		
					stream.forEach(al::add);
		
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			List<String> roads;
			Set<String> originRoads = new HashSet<String>();
			Set<String> destinationRoads = new HashSet<String>();
			for (String s : al) {
				roads = Arrays.asList(s.split(", "));
				if (roads.contains(origin)) {
					originRoads.addAll(roads);
				}
			}
			
			if (!originRoads.isEmpty()) {
				for (String s : al) {
					roads = Arrays.asList(s.split(", "));
					if (roads.contains(destination)) {
						destinationRoads.addAll(roads);
					}
				}
				
				if (!destinationRoads.isEmpty()) {
					destinationRoads.retainAll(originRoads);
					if (!destinationRoads.isEmpty())
						output = "yes";
				}
			}
		}

		return output;
    }
}
