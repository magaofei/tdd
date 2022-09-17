package io.github.magaofei;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgsTest {
    
    // -l -p 8080 -d /usr/logs
    // [-l], [-p, 8080], [-d, /usr/logs]
    // {-l:[], -p:[8080], -d:[/usr/logs]}
    // Single Option:
    @Test
    void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption booleanOption = Args.parse(BooleanOption.class, "-l");
        assertTrue(booleanOption.logging());
    }
    
    @Test
    void should_set_boolean_option_to_false_if_flag_not_present() {
        BooleanOption booleanOption = Args.parse(BooleanOption.class);
        assertFalse(booleanOption.logging());
    }
    
    public static record BooleanOption(@Option("l") boolean logging) {
    }
    
    @Test
    void should_parse_int_as_option_value() {
        IntOption intOption = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, intOption.port());
    }
    record IntOption(@Option("p") int port) {
    
    }
    
    @Test
    void should_parse_string_as_option_value() {
        StringOption stringOption = Args.parse(StringOption.class, "-d", "/usr/log");
        assertEquals("/usr/log", stringOption.directory());
    }
    
    record StringOption(@Option("d") String directory) {
    
    }
    // TODO: multi options: -l -p 8080 -d /usr/logs
    // sad path
    // -bool -l t / -l t f
    // default value
    // - bool : false
    // - int : 0
    // - string : ""
    @Disabled
    @Test
    void should_example_1() {
        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        
        assertTrue(options.logging());
        assertEquals(8080, options.port());
    }
    
    @Disabled
    @Test
    void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertArrayEquals(new String[]{"-l", "-p", "8080", "-d", "/usr/logs"}, options.group());
        assertArrayEquals(new int[]{1, 2, 3, 4,5}, options.decimals());
    }
    
    record Options(@Option("l")boolean logging, @Option("p")int port, @Option("d")String directory) {
    
    }
    
    record ListOptions(@Option("l")String[] group, @Option("p")int[] decimals) {
    
    }
    
}
