import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator
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
            return re*re+im*im;
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
            a.re = Math.abs(a.re);
            a.im = Math.abs(a.im);
            b.re = Math.abs(b.re);
            b.im = Math.abs(b.im);
            double real = a.re * b.re - a.im * b.im;
            double imag = a.re * b.im + a.im * b.re;
            return new Complex(real,imag);
        }
        public Complex conjugate()
        {
            this.im = -this.im;
            return this;
        }
        public boolean EqualsTo(Complex b)
        {
            if(this.re == b.re && this.im == -b.im)
                return true;
            return false;
        }
    }
    @Override
    // This sets initial range to (-2 - 1.5i) - (1 + 1.5i)
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
    }
    public static final int MAX_ITERATIONS = 2000;
    @Override
    public int numIterations(double x, double y) {
        Complex z0 = new Complex(x, y);
        Complex z = new Complex(0, 0);
        Complex temp;
        //double dZ = z0.abs();
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            if (z.abs() > 4.0) return i;
            // temp = z;
            z = z.times(z).plus(z0);
            // z0 = temp;
            //if(z.EqualsTo(z0)) {
            //    //temp = z;
            //    z = z.times(z).plus(z0);
            //    z0 = z;
            //}
        }
        return -1;
    }
}