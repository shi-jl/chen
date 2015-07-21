import java.awt.*;
import java.applet.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class read_wav_udp extends Applet
{
  TextField tex0;
  Button but1,but2,but3;
  //WavFile h1=new WavFile();
  //UdpData udph1=new UdpData();
  int ch=0,len=0,fre=0,bit=0;
  double t=0,a1=0,a2=0;
  short x1[]=new short[16385];
  short x2[]=new short[16385];
  byte buffer[]=new byte[64540];
  int par[]=new int[5];

  public read_wav_udp(){}

  public void init()
  {
    Font NewFnt=new Font("Roman",Font.PLAIN,12);
    this.setFont(NewFnt);
    resize(540,300);
    setLayout(null);
    //udph1=new UdpData();
    tex0=new TextField("");
    add(tex0);
    tex0.reshape(28,10,500,20);
    but1=new Button("Ding");
    add(but1);
    but1.reshape(130,260,60,20);
    but2=new Button("Chord");
    add(but2);
    but2.reshape(230,260,60,20);
    but3=new Button("UDP");
    add(but3);
    but3.reshape(330,260,60,20);
    data("ding.wav");
  }

  public boolean action(Event evt,Object o)
  {
    if(evt.target==but1)
      data("ding.wav");
    if(evt.target==but2)
      data("chord.wav");
    if(evt.target==but3)
      data1();
    repaint();
    return true;
  }

  public void data(String name)
  {
    URL url1;
    try
    {
      url1=new URL(getDocumentBase(),name);
    }
    catch(Exception e)
    {
      url1=getDocumentBase();
    }
    //h1.read(url1,buffer,x1,x2,16384,par);
    ch=par[0];
    fre=par[1];
    len=par[2];
    bit=par[3];
  }

  public void data1()
  {
    String host;
    host=getCodeBase().getHost();
    //udph1.read(host,buffer,x1);
    ch=1;
    fre=11025;
    len=1024;
    bit=16;
  }

  public void paint(Graphics g)
  {
    drawwave(g);
    tex0.setText("CH="+ch+",Fs="+fre+",Len="+len+",Bit="+bit);
  }

  public void drawwave(Graphics g)
  {
    int i,xx1,xx2,yy1,yy2;
    double ff=1,sa,la,k,mm;
    g.setColor(Color.lightGray);
    g.fillRect(0,0,600,400);
    g.setColor(Color.black);
    g.drawRect(28,40,500,200);
    g.drawString("A",14,50);
    g.drawString("-A",8,240);
    g.drawString("0",14,145);
    g.drawString("0",30,255);
    g.drawString("T",525,255);
    t=500.0/fre;

    if(ch==0)
      return;

    sa=la=x1[1];
    for(i=1;i<500;i++)
    {
      if(sa>x1[i])
        sa=x1[i];
      if(la<x1[i])
        la=x1[i];
    }

    a1=Math.max(Math.abs(sa),Math.abs(la));
    k=1.2*a1;
    ff=100/k;
    g.setColor(Color.red);

    for(i=1;i<500;i++)
    {
      xx1=28+i;
      yy1=(int)(140-x1[i]*ff);
      xx2=29+i;
      yy2=(int)(140-x1[i+1]*ff);
      g.drawLine(xx1,yy1,xx2,yy2);
    }

    if(ch==1)
      return;

    sa=la=x2[1];
    for(i=1;i<500;i++)
    {
      if(sa>x2[i])
        sa=x2[i];
      if(la<x2[i])
        la=x2[i];
    }

    a2=Math.max(Math.abs(sa),Math.abs(la));
    k=1.2*a2;
    ff=100.0/k;
    g.setColor(Color.blue);

    for(i=i;i<500;i++)
    {
      xx1=28+i;
      yy1=(int)(140-x2[i]*ff);
      xx2=29+i;
      yy2=(int)(140-x2[i+1]*ff);
      g.drawLine(xx1,yy1,xx2,yy2);
    }
  }
}