package test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.Server;

import java.io.IOException;

public class ServerTest {

    @Test
    public void createServerWhenPortIsNegativeShouldThrowIllegalArgument() {
        //arrange
        int port = -1;
        //assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            // act
            new Server(port);
        });

    }

    @Test
    public void createServerWhenPortIsTooHighShouldThrowIllegalArgument() {
        //arrange
        int port = 67000;
        //assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           //act
            new Server(port);
        });

    }
    @Test
    public void createServerWhenPortIsInRangeShouldNotBeNull() throws IOException {
        //arrange
        int port = 2345;
        //act
        Server s = new Server(port);
        //assert
        Assertions.assertNotNull(s);


    }
}
