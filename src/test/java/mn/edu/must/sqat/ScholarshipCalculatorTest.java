package mn.edu.must.sqat;

// ЛАБ 11 — B1 + B2: assertion-ууд яг утгатай, зааг утгууд нэмэгдсэн тестийн багц.
// B1: сул шалгалтууд (assertNotNull, assertTrue(муж)) assertEquals болсон.
// B2: заагийн (boundary) тестүүд нэмсэн — үсгийн дүн, gpa, scholarship босго, оролтын хязгаар.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

    ScholarshipCalculator calc;

    @BeforeEach
    void setUp() { calc = new ScholarshipCalculator(); }

    // ---- letterGrade: үсгэн дүн яг таарах ----

    @Test
    void letterGrade_exactGradeForEachRange() {
        assertEquals("A", calc.letterGrade(95));
        assertEquals("B", calc.letterGrade(85));
        assertEquals("C", calc.letterGrade(75));
        assertEquals("D", calc.letterGrade(65));
        assertEquals("F", calc.letterGrade(30));
    }

    @Test
    void letterGrade_gradeBoundaries() {
        assertEquals("A", calc.letterGrade(90));
        assertEquals("B", calc.letterGrade(89.99));
        assertEquals("B", calc.letterGrade(80));
        assertEquals("C", calc.letterGrade(79.99));
        assertEquals("C", calc.letterGrade(70));
        assertEquals("D", calc.letterGrade(69.99));
        assertEquals("D", calc.letterGrade(60));
        assertEquals("F", calc.letterGrade(59.99));
    }

    @Test
    void letterGrade_inputLimits() {
        assertEquals("F", calc.letterGrade(0));
        assertEquals("A", calc.letterGrade(100));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-0.01));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(100.01));
    }

    @Test
    void letterGrade_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(-1));
        assertThrows(IllegalArgumentException.class, () -> calc.letterGrade(101));
    }

    // ---- gpa: яг утга ----

    @Test
    void gpa_exactValues() {
        assertEquals(3.5, calc.gpa(new double[]{95, 85}), 1e-9); // (4 + 3) / 2
        assertEquals(1.5, calc.gpa(new double[]{75, 65}), 1e-9); // (2 + 1) / 2
        assertEquals(0.0, calc.gpa(new double[]{40}), 1e-9);     // F = 0
    }

    @Test
    void gpa_fullAndZeroMarks() {
        assertEquals(4.0, calc.gpa(new double[]{100}), 1e-9);    // A = 4
        assertEquals(0.0, calc.gpa(new double[]{0}), 1e-9);      // F = 0
    }

    @Test
    void gpa_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(null));
        assertThrows(IllegalArgumentException.class, () -> calc.gpa(new double[]{}));
    }

    // ---- scholarship: яг утга ----

    @Test
    void scholarship_exactValues() {
        assertEquals(100, calc.scholarship(3.9, 16, false)); // босго 3.8/15
        assertEquals(70, calc.scholarship(3.5, 13, false));  // босго 3.4/12
        assertEquals(50, calc.scholarship(3.1, 5, false));   // босго 3.0
        assertEquals(0, calc.scholarship(2.0, 1, false));    // босгонд хүрээгүй
        assertEquals(0, calc.scholarship(4.0, 20, true));    // сахилгын зөрчил
    }

    @Test
    void scholarship_thresholdBoundaries() {
        assertEquals(100, calc.scholarship(3.8, 15, false)); // яг 3.8 / 15
        assertEquals(70, calc.scholarship(3.8, 14, false));  // кредит 14 < 15 → 70-ийн дүрэм
        assertEquals(70, calc.scholarship(3.79, 15, false)); // gpa 3.79 < 3.8 → 70-ийн дүрэм
        assertEquals(70, calc.scholarship(3.4, 12, false));  // яг 3.4 / 12
        assertEquals(50, calc.scholarship(3.4, 11, false));  // кредит 11 < 12 → 50-ийн дүрэм
        assertEquals(50, calc.scholarship(3.39, 12, false)); // gpa 3.39 < 3.4 → 50-ийн дүрэм
        assertEquals(50, calc.scholarship(3.0, 0, false));   // яг 3.0, кредит 0
        assertEquals(0, calc.scholarship(2.99, 20, false));  // gpa 2.99 < 3.0 → 0
    }

    @Test
    void scholarship_inputLimits() {
        assertEquals(0, calc.scholarship(0.0, 0, false));    // gpa 0 ба кредит 0 хүчинтэй
        assertEquals(100, calc.scholarship(4.0, 20, false)); // gpa 4 хүчинтэй
    }

    @Test
    void scholarship_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 10, false));
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(-0.1, 10, false));
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.0, -1, false));
    }
}
