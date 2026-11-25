package mn.edu.must.sqat;

// ЛАБ 11 — B1: assertion-уудыг ЯГ УТГЫН болгосон тестийн багц.
// Starter-ийн сул шалгалтууд (assertNotNull, assertTrue(муж)) бүгд assertEquals болсон.
// Зааг утгын тестүүд (B2) энэ шатанд нэмэгдээгүй.

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
    void scholarship_invalidInputs_throw() {
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(4.1, 10, false));
        assertThrows(IllegalArgumentException.class, () -> calc.scholarship(3.0, -1, false));
    }
}
