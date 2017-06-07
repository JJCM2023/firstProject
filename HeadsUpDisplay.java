import java.awt.Color;
import java.awt.Graphics;
/**
 * Write a description of class HeadsUpDisplay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HeadsUpDisplay
{
    public static int Health=100;
    public static int BossHealth=200;
    public static int FireBossHealth=400;
    public static int IceBossHealth=600;
    public static int IonHealth=800;
    public static int CinHealth=1200;
    public static boolean isBoss=false;
    public static boolean isFireBoss=false;
    public static boolean isIceBoss=false;
    public static boolean isIon=false;
    public static boolean isCin=false;
    private int part=0;
    static int score=0;
    public void tick()
    {
        Health=Game.clamp(Health,0,100);
        BossHealth=Game.clamp(BossHealth,0,200);
        FireBossHealth=Game.clamp(FireBossHealth,0,400);
        IceBossHealth=Game.clamp(IceBossHealth,0,600);
        IonHealth=Game.clamp(IonHealth,0,800);
        CinHealth=Game.clamp(CinHealth,0,1200);
        part++;
        if(part%100==0)
        {
            score++;
        }
        /*if(isCin&&FireBossHealth<200&&FireBossHealth>0)
        {
            FireBossHealth=199;
        }
        if(isCin&&IceBossHealth<200&&IceBossHealth>0)
        {
            IceBossHealth=199;
        }
        if(isCin&&IonHealth<200&&IonHealth>0)
        {
            IonHealth=199;
        }*/
    }
    public void reset()
    {
        isBoss=false;
        isFireBoss=false;
        isIceBoss=false;
        isIon=false;
        isCin=false;
        part=0;
        score=0;
    }
    public void render(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(Game.WIDTH-(32+15),270-(102),32,204);
        g.setColor(Color.red);
        g.fillRect(Game.WIDTH-(28+15),170,26,200);
        g.setColor(Color.green);
        g.fillRect(Game.WIDTH-(28+15),170,26,Health*2);
        g.setColor(Color.yellow);
        g.drawString("Score: "+score, 15,15);
        g.drawString("Health: "+Health, 15,45);
        if(isBoss)
        {
             g.setColor(Color.black);
             g.fillRect(Game.WIDTH-(64+15),270-(102),32,204);
             g.setColor(Color.white);
             g.fillRect(Game.WIDTH-(60+15),170,26,200);
             g.setColor(Color.red);
             g.fillRect(Game.WIDTH-(60+15),170,26,BossHealth);
             if(BossHealth==0)
             {
                 g.setColor(Color.blue);
                 g.fillRect(Game.WIDTH-(64+15),270-(102),32,204);
             }
        }
        if(isFireBoss)
        {
             g.setColor(Color.black);
             g.fillRect(Game.WIDTH-(64+15),270+(102),32,204);
             g.setColor(Color.white);
             g.fillRect(Game.WIDTH-(60+15),270+104,26,200);
             g.setColor(Color.red);
             g.fillRect(Game.WIDTH-(60+15),270+104,26,FireBossHealth/2);
             if(FireBossHealth==0)
             {
                 g.setColor(Color.blue);
                 g.fillRect(Game.WIDTH-(64+15),270+(102),32,204);
             }
        }
        if(isIceBoss)
        {
             g.setColor(Color.black);
             g.fillRect(Game.WIDTH-(64+15),0,32,204);
             g.setColor(Color.white);
             g.fillRect(Game.WIDTH-(60+15),2,26,200);
             g.setColor(Color.red);
             g.fillRect(Game.WIDTH-(60+15),2,26,IceBossHealth/3);
             if(IceBossHealth==0)
             {
                 g.setColor(Color.blue);
                 g.fillRect(Game.WIDTH-(64+15),0,32,204);
             }
        }
        if(isIon)
        {
             g.setColor(Color.white);
             g.fillRect(Game.WIDTH-(96+15),270-(102),32,204);
             g.setColor(Color.black);
             g.fillRect(Game.WIDTH-(94+15),170,26,200);
             g.setColor(Color.yellow);
             g.fillRect(Game.WIDTH-(94+15),170,26,IonHealth/4);
             if(IonHealth==0)
             {
                 g.setColor(Color.blue);
                 g.fillRect(Game.WIDTH-(96+15),270-(102),32,204);
             }
        }
        if(isCin)
        {
             g.setColor(Color.green);
             g.fillRect(Game.WIDTH-(64+15),270-(102),32,204);
             g.setColor(Color.white);
             g.fillRect(Game.WIDTH-(60+15),170,26,200);
             g.setColor(Color.red);
             g.fillRect(Game.WIDTH-(60+15),170,26,CinHealth/6);
             if(CinHealth==0)
             {
                 g.setColor(Color.blue);
                 g.fillRect(Game.WIDTH-(64+15),270-(102),32,204);
             }
        }
    }
    public int getScore()
    {
        return score;
    }
    public void setIsBoss(boolean boss)
    {
        isBoss=boss;
    }
    public void setIsFireBoss(boolean boss)
    {
        isFireBoss=boss;
    }
    public void setIsIceBoss(boolean boss)
    {
        isIceBoss=boss;
    }
    public void setIsIon(boolean boss)
    {
        isIon=boss;
    }
    public void setIsCin(boolean boss)
    {
        isCin=boss;
    }
}
