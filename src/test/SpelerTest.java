package test;

import domein.Kaart;
import domein.Speler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpelerTest {

    Speler s;

    @BeforeEach
    public void init()
    {
        s = new Speler("Jan");
        List<Kaart> kaarten = new ArrayList<>(Arrays.asList(
                new Kaart("groen"), new Kaart("groen"), new Kaart("groen"), new Kaart("groen"),
                new Kaart("geel"), new Kaart("blauw"), new Kaart("oranje"), new Kaart("grijs"),
                new Kaart("+2"), new Kaart("groen")));
        s.getKaarten().addAll(kaarten);
    }

    @Test
    void berekenScore_ValidInput_OK()
    {
        assertEquals(17, s.berekenScore());
    }
}