import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgsTest {
    
    // -l -p 8080 -d /usr/logs
    // [-l], [-p, 8080], [-d, /usr/logs]
    // {-l:[], -p:[8080], -d:[/usr/logs]}
    // Single Option:
    // TODO: -- bool
    // TODO: -- int
    // TODO: -- string
    // multi options: -l -p 8080 -d /usr/logs
    // sad path
    // -bool -l t / -l t f
    // default value
    // - bool : false
    // - int : 0
    // - string : ""
    @Test
    void should() {
        Arguments args = Args.parse("l:b,p:d,d:s,", "-l", "-p", "8080", "-d", "/usr/logs");
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        
        assertTrue(options.logging());
        assertEquals(8080, options.port());
    }
    
    @Test
    void should_example() {
        ListOptions options = Args.parse(ListOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertArrayEquals(new String[]{"-l", "-p", "8080", "-d", "/usr/logs"}, options.group());
        assertArrayEquals(new int[]{1, 2, 3, 4,5}, options.decimals());
    }
    
    record Options(@Option("l")boolean logging, @Option("p")int port, @Option("d")String directory) {
    
    }
    
    record ListOptions(@Option("l")String[] group, @Option("p")int[] decimals) {
    
    }
    
}
