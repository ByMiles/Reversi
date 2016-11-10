package GUI;


public class Thread_Resizer implements Runnable
{
    private Frame frame;
    private int width, height;

    public Thread_Resizer(Frame frame)
    {
        this.frame = frame;
        this.width = frame.getContentPane().getWidth();
        this.height = frame.getContentPane().getHeight();
    }
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (Exception ignored){}

            if(this.width != this.frame.getContentPane().getWidth() || this.height != this.frame.getContentPane().getHeight())
            {
                this.width = this.frame.getContentPane().getWidth();
                this.height = this.frame.getContentPane().getHeight();
                frame.resizeIt(this.width, this.height);
            }

        }
    }
}
