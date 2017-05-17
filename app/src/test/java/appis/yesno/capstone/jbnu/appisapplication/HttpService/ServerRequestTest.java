package appis.yesno.capstone.jbnu.appisapplication.HttpService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by charlesn on 2017-04-30.
 */
public class ServerRequestTest {
    ServerRequest serverRequest;

    @Before
    public void setUp() throws Exception {
        serverRequest = ServerRequest.getInstance();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getInstance() throws Exception {
        assertEquals(serverRequest, ServerRequest.getInstance());
    }

    @Test
    public void getIDENTIFY() throws Exception {
        assertEquals(serverRequest.getIDENTIFY(), serverRequest.getIDENTIFY());
    }

    @Test
    public void setIDENTIFY() throws Exception {

    }

    @Test
    public void getCarNum() throws Exception {

    }

    @Test
    public void setCarNum() throws Exception {

    }

    @Test
    public void getUserPassword() throws Exception {

    }

    @Test
    public void setUserPassword() throws Exception {

    }

    @Test
    public void getUser() throws Exception {

    }

    @Test
    public void setUser() throws Exception {

    }

}