import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator
{
    // Class for complex number operations - abs, multiply etc.
    private class Complex
    {
        public double re;
        public double im;
        public Complex(double real, double imag)
        {
            re = real;
            im = imag;
        }
        public double abs()
        {
            return re * re + im * im;
        }
        public void set(double real,double imag)
        {
            re = real;
            im = imag;
        }
        public Complex plus(Complex b)
        {
            Complex a = this;
            double real = a.re + b.re;
            double imag = a.im + b.im;
            return new Complex(real,imag);
        }
        public Complex times(Complex b)
        {
            Complex a = this;
            double real = a.re * b.re - a.im * b.im;
            double imag = a.re * b.im + a.im * b.re;
            return new Complex(real,imag);
        }
    }
    @Override
    // This sets initial range to (-2 - 1.5i) - (1 + 1.5i)
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }
    public static final int MAX_ITERATIONS = 2000;
    @Override
    public int numIterations(double x, double y) {
        Complex z0 = new Complex(x,y);
        Complex z = z0;
        for (int i = 0;i < MAX_ITERATIONS;i++)
        {
            if(z.abs() > 4.0) return i;
            z = z.times(z).plus(z0);
        }
        return -1;
    }
}
