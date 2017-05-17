package appis.yesno.capstone.jbnu.appisapplication.Encript;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by charlesn on 2017-04-30.
 */
public class EncryptTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void encrypt_SHA1() throws Exception {
        assertEquals("asdf", Encrypt.encrypt_SHA1("1"));
    }

}