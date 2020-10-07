import logtest.MainApp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MetodTests {
    private MainApp mainApp;
    Integer[] arr1 = {5, 6, 7, 8, 44, 9};
    int[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 44, 9};
    Integer[] arr2 = {8, 44, 9};
    int[] array2 = {1, 2, 3, 4, 5, 6, 4, 8, 44, 9};

    int[] arrCheck1 = {1, 1, 4, 1, 1, 4, 4, 1, 4, 4, 4, 1};
    int[] arrCheck2 = {1, 1, 1, 1, 1, 1};
    int[] arrCheck3 = {1, 1, 4, 2, 1, 4, 4, 1, 4, 4, 4, 1};
    int[] arrCheck4 = {4, 4, 4, 4, 4, 4};

    @Before
    public void init() {
        mainApp = new MainApp();
    }

    @Test
    public void testTransformArray1() {
        Assert.assertArrayEquals(arr1, mainApp.transformArray(array1));
    }

    @Test
    public void testTransformArray2() {
        Assert.assertArrayEquals(arr2, mainApp.transformArray(array2));
    }


    @Test
    public void testCheckArray1() {
        Assert.assertEquals(true, mainApp.checkArray(arrCheck1));
    }

    @Test
    public void testCheckArray2() {
        Assert.assertEquals(true, mainApp.checkArray(arrCheck2));
    }

    @Test
    public void testCheckArray3() {
        Assert.assertEquals(true, mainApp.checkArray(arrCheck3));
    }

    @Test
    public void testCheckArray4() {
        Assert.assertEquals(true, mainApp.checkArray(arrCheck4));
    }

}
