package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {

    private final ScholarshipCalculator calculator = new ScholarshipCalculator();

    // =========================
    // letterGrade() tests
    // =========================

    @Test
    void letterGrade_A_Grade() {
        assertEquals("A", calculator.letterGrade(90));
        assertEquals("A", calculator.letterGrade(95));
        assertEquals("A", calculator.letterGrade(100));
    }

    @Test
    void letterGrade_B_Grade() {
        assertEquals("B", calculator.letterGrade(80));
        assertEquals("B", calculator.letterGrade(85));
        assertEquals("B", calculator.letterGrade(89.99));
    }

    @Test
    void letterGrade_C_Grade() {
        assertEquals("C", calculator.letterGrade(70));
        assertEquals("C", calculator.letterGrade(75));
        assertEquals("C", calculator.letterGrade(79.99));
    }

    @Test
    void letterGrade_D_Grade() {
        assertEquals("D", calculator.letterGrade(60));
        assertEquals("D", calculator.letterGrade(65));
        assertEquals("D", calculator.letterGrade(69.99));
    }

    @Test
    void letterGrade_F_Grade() {
        assertEquals("F", calculator.letterGrade(0));
        assertEquals("F", calculator.letterGrade(30));
        assertEquals("F", calculator.letterGrade(59.99));
    }

    @Test
    void letterGrade_InvalidScore_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.letterGrade(-1));

        assertThrows(IllegalArgumentException.class,
                () -> calculator.letterGrade(101));
    }

    // =========================
    // gpa() tests
    // =========================

    @Test
    void gpa_AllA_Returns4() {
        assertEquals(4.0, calculator.gpa(new double[]{90, 95, 100}));
    }

    @Test
    void gpa_AllB_Returns3() {
        assertEquals(3.0, calculator.gpa(new double[]{80, 85, 89}));
    }

    @Test
    void gpa_AllC_Returns2() {
        assertEquals(2.0, calculator.gpa(new double[]{70, 75, 79}));
    }

    @Test
    void gpa_AllD_Returns1() {
        assertEquals(1.0, calculator.gpa(new double[]{60, 65, 69}));
    }

    @Test
    void gpa_AllF_Returns0() {
        assertEquals(0.0, calculator.gpa(new double[]{0, 30, 59}));
    }

    @Test
    void gpa_MixedGrades_ReturnsCorrectAverage() {
        double[] scores = {95, 85, 75, 65, 50};
        assertEquals(2.0, calculator.gpa(scores));
    }

    @Test
    void gpa_NullArray_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.gpa(null));
    }

    @Test
    void gpa_EmptyArray_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.gpa(new double[]{}));
    }

    @Test
    void gpa_InvalidScoreInArray_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.gpa(new double[]{90, 105}));

        assertThrows(IllegalArgumentException.class,
                () -> calculator.gpa(new double[]{90, -5}));
    }

    // =========================
    // scholarship() tests
    // =========================

    @Test
    void scholarship_HasViolation_Returns0() {
        assertEquals(0, calculator.scholarship(4.0, 20, true));
        assertEquals(0, calculator.scholarship(3.0, 15, true));
    }

    @Test
    void scholarship_GPA38AndCredits15_Returns100() {
        assertEquals(100, calculator.scholarship(3.8, 15, false));
        assertEquals(100, calculator.scholarship(4.0, 20, false));
    }

    @Test
    void scholarship_GPA38ButInsufficientCredits_Returns70() {
        assertEquals(70, calculator.scholarship(3.8, 14, false));
    }

    @Test
    void scholarship_GPA34AndCredits12_Returns70() {
        assertEquals(70, calculator.scholarship(3.4, 12, false));
        assertEquals(70, calculator.scholarship(3.6, 12, false));
    }

    @Test
    void scholarship_GPA34ButInsufficientCredits_Returns50() {
        assertEquals(50, calculator.scholarship(3.4, 11, false));
    }

    @Test
    void scholarship_GPA30_Returns50() {
        assertEquals(50, calculator.scholarship(3.0, 0, false));
        assertEquals(50, calculator.scholarship(3.3, 10, false));
    }

    @Test
    void scholarship_GPALessThan30_Returns0() {
        assertEquals(0, calculator.scholarship(2.9, 20, false));
        assertEquals(0, calculator.scholarship(0, 0, false));
    }

    @Test
    void scholarship_InvalidGPA_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(-0.1, 15, false));

        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(4.1, 15, false));
    }

    @Test
    void scholarship_NegativeCredits_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(3.8, -1, false));
    }

    @Test
    void scholarship_BoundaryGPAValues() {
        assertEquals(100, calculator.scholarship(3.8, 15, false));
        assertEquals(70, calculator.scholarship(3.4, 12, false));
        assertEquals(50, calculator.scholarship(3.0, 10, false));
        assertEquals(0, calculator.scholarship(2.99, 20, false));
    }
}