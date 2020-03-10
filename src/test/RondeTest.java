package test;

import domein.Ronde;
import domein.Speler;
import domein.Stapel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RondeTest {

    private Ronde r;
    @BeforeEach
    void setUp()
    {
        List<Speler> s = new ArrayList<>(Arrays.asList(new Speler("test"), new Speler("test"), new Speler("test"), new Speler("test")));
        r = new Ronde(s);
        List<Stapel> st = r.getStapels();
        r.neemStapel(st.get(0));
        r.neemStapel(st.get(1));
        r.neemStapel(st.get(2));
        r.neemStapel(st.get(3));
    }

    @Test
    void zijnAlleStapelsGenomen()
    {
        assertTrue(r.zijnAlleStapelsGenomen());
    }
}