
//Version 1.2 Winter 2008

import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;

// Java extension packages
import javax.swing.*;
import java.awt.image.WritableRaster;
import javax.imageio.stream.FileImageOutputStream;
import java.util.Iterator;
import java.util.Vector;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;



// class to display an Image on a panel
public class IP_Handout2012 extends JFrame implements ActionListener {
    
    static JDesktopPane theDesktop;
    static ImagePro img;
    static Vector img_vector = new Vector();

    public IP_Handout2012() {
        super("CS 3060 - Image Processing Software Shell ");

        // create menu bar, menu and menu item
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File Stuff");
        JMenu pMenu = new JMenu("Point Transforms");
        JMenu cMenu = new JMenu("Features");
        JMenu fMenu = new JMenu("Fourier");
        JMenu mMenu = new JMenu("Morph");

        // add the File Menu items and listeners
        menuBar.add(fileMenu);

        JMenuItem file_load = new JMenuItem("Load Image");
        file_load.addActionListener(this);
        fileMenu.add(file_load);

        JMenuItem file_save = new JMenuItem("Save Image");
        file_save.addActionListener(this);
        fileMenu.add(file_save);

        JMenuItem file_save_as = new JMenuItem("Save Image As");
        file_save_as.addActionListener(this);
        fileMenu.add(file_save_as);

        fileMenu.addSeparator();

        JMenuItem file_exit = new JMenuItem("Exit");
        file_exit.addActionListener(this);
        fileMenu.add(file_exit);

        // add the Lab1 Menu items and listeners
        menuBar.add(pMenu);

        JMenuItem mirror_x = new JMenuItem("Mirror on X");
        mirror_x.addActionListener(this);
        pMenu.add(mirror_x);

        JMenuItem mirror_y = new JMenuItem("Mirror on Y");
        mirror_y.addActionListener(this);
        pMenu.add(mirror_y);

        JMenuItem rotate_l = new JMenuItem("Rotate Left");
        rotate_l.addActionListener(this);
        pMenu.add(rotate_l);

        JMenuItem rotate_r = new JMenuItem("Rotate Right");
        rotate_r.addActionListener(this);
        pMenu.add(rotate_r);

        pMenu.addSeparator();

        JMenu pitMenu = new JMenu("Pixel Intensity Transforms");
        pitMenu.addActionListener(this);
        pMenu.add(pitMenu);

        JMenuItem change = new JMenuItem("Brightness Shift");
        change.addActionListener(this);
        pitMenu.add(change);

        JMenuItem negative = new JMenuItem("Negative");
        negative.addActionListener(this);
        pitMenu.add(negative);

        JMenuItem poster = new JMenuItem("Posterize");
        poster.addActionListener(this);
        pitMenu.add(poster);

        JMenuItem bitClip = new JMenuItem("Bit Clipping");
        bitClip.addActionListener(this);
        pitMenu.add(bitClip);

        JMenuItem parabola = new JMenuItem("Parabolic");
        parabola.addActionListener(this);
        pitMenu.add(parabola);

        JMenu histMenu = new JMenu("Histogram Transforms");
        histMenu.addActionListener(this);
        pMenu.add(histMenu);

        JMenuItem c_stretch = new JMenuItem("Contrast Stretch");
        c_stretch.addActionListener(this);
        histMenu.add(c_stretch);

        JMenuItem l_transform = new JMenuItem("Linear Transform");
        l_transform.addActionListener(this);
        histMenu.add(l_transform);

        JMenuItem gammaC = new JMenuItem("Gamma Correction");
        gammaC.addActionListener(this);
        histMenu.add(gammaC);

        JMenuItem histoEqual = new JMenuItem("Histogram Equalization");
        histoEqual.addActionListener(this);
        histMenu.add(histoEqual);

        JMenu bceMenu = new JMenu("BCE - Techniques");
        bceMenu.addActionListener(this);
        pMenu.add(bceMenu);

        JMenuItem simpleBCE = new JMenuItem("Simple BCE");
        simpleBCE.addActionListener(this);
        bceMenu.add(simpleBCE);

        JMenuItem isoBCE = new JMenuItem("Iso Contour Highlighting");
        isoBCE.addActionListener(this);
        bceMenu.add(isoBCE);

        JMenuItem bsBCE = new JMenuItem("Brightness Slicing");
        bsBCE.addActionListener(this);
        bceMenu.add(bsBCE);

        JMenu bit_planesMenu = new JMenu("Bit Planes");
        bit_planesMenu.addActionListener(this);
        pMenu.add(bit_planesMenu);

        JMenuItem bit_planes1 = new JMenuItem("Keep Pixel bit value");
        bit_planes1.addActionListener(this);
        bit_planesMenu.add(bit_planes1);

        JMenuItem bit_planes2 = new JMenuItem("Black and White");
        bit_planes2.addActionListener(this);
        bit_planesMenu.add(bit_planes2);

        pMenu.addSeparator();

        JMenuItem addhisto = new JMenuItem("Add Histogram");
        addhisto.addActionListener(this);
        pMenu.add(addhisto);

        JMenuItem info = new JMenuItem("Image Info");
        info.addActionListener(this);
        pMenu.add(info);

        JMenuItem clone_img = new JMenuItem("Clone Image");
        clone_img.addActionListener(this);
        pMenu.add(clone_img);

        JMenuItem restore = new JMenuItem("Restore Image");
        restore.addActionListener(this);
        pMenu.add(restore);
        
        menuBar.add(cMenu);
        
        JMenuItem pixelate = new JMenuItem("Pixelate");
        pixelate.addActionListener(this);
        cMenu.add(pixelate);
        
        JMenuItem impulsenoise = new JMenuItem("Impulse Noise");
        impulsenoise.addActionListener(this);
        cMenu.add(impulsenoise);
        
        cMenu.addSeparator();
        
        JMenuItem alphablend = new JMenuItem("Alpha Blend");
        alphablend.addActionListener(this);
        cMenu.add(alphablend);
        
        JMenuItem watermark = new JMenuItem("Watermark");
        watermark.addActionListener(this);
        cMenu.add(watermark);
        
        cMenu.addSeparator();
        
        JMenu uMenu = new JMenu("Binary");
        uMenu.addActionListener(this);
        cMenu.add(uMenu);
        
        JMenuItem difference = new JMenuItem("Difference");
        difference.addActionListener(this);
        uMenu.add(difference);
        
        JMenuItem addition = new JMenuItem("Addition");
        addition.addActionListener(this);
        uMenu.add(addition);
        
        JMenu logMenu = new JMenu("Logical");
        logMenu.addActionListener(this);
        cMenu.add(logMenu);
        
        JMenuItem and = new JMenuItem("AND");
        and.addActionListener(this);
        logMenu.add(and);
        
        JMenuItem or = new JMenuItem("OR");
        or.addActionListener(this);
        logMenu.add(or);
        
        JMenuItem xor = new JMenuItem("XOR");
        xor.addActionListener(this);
        logMenu.add(xor);
        
        JMenu statMenu = new JMenu("Statistical");
        statMenu.addActionListener(this);
        cMenu.add(statMenu);
        
        JMenuItem medianfilter = new JMenuItem("Median Filter");
        medianfilter.addActionListener(this);
        statMenu.add(medianfilter);
        
        JMenuItem modefilter = new JMenuItem("Mode Filter");
        modefilter.addActionListener(this);
        statMenu.add(modefilter);
        
        JMenu maskMenu = new JMenu("Image Mask");
        maskMenu.addActionListener(this);
        cMenu.add(maskMenu);
        
        JMenuItem imagemask0 = new JMenuItem("LOW Pass 1");
        imagemask0.addActionListener(this);
        maskMenu.add(imagemask0);
        
        JMenuItem imagemask1 = new JMenuItem("LOW Pass 2");
        imagemask1.addActionListener(this);
        maskMenu.add(imagemask1);
        
        JMenuItem imagemask2 = new JMenuItem("LOW Pass 3");
        imagemask2.addActionListener(this);
        maskMenu.add(imagemask2);
        
        JMenuItem imagemask3 = new JMenuItem("HIGH Pass 1");
        imagemask3.addActionListener(this);
        maskMenu.add(imagemask3);
        
        JMenuItem imagemask4 = new JMenuItem("HIGH Pass 2");
        imagemask4.addActionListener(this);
        maskMenu.add(imagemask4);
        
        JMenuItem imagemask5 = new JMenuItem("Laplacian 1");
        imagemask5.addActionListener(this);
        maskMenu.add(imagemask5);
        
        JMenuItem imagemask6 = new JMenuItem("Laplacian 2");
        imagemask6.addActionListener(this);
        maskMenu.add(imagemask6);
        
        JMenuItem imagemask7 = new JMenuItem("Shift 1");
        imagemask7.addActionListener(this);
        maskMenu.add(imagemask7);
        
        JMenuItem imagemask8 = new JMenuItem("Shift 2");
        imagemask8.addActionListener(this);
        maskMenu.add(imagemask8);
        
        JMenuItem imagemask9 = new JMenuItem("Shift 3");
        imagemask9.addActionListener(this);
        maskMenu.add(imagemask9);
        
        JMenuItem imagemask10 = new JMenuItem("Vertical Mask");
        imagemask10.addActionListener(this);
        maskMenu.add(imagemask10);
        
        JMenuItem imagemask11 = new JMenuItem("Horizontal Mask");
        imagemask11.addActionListener(this);
        maskMenu.add(imagemask11);
        
        JMenuItem unsharpmask = new JMenuItem("Unsharp Mask");
        unsharpmask.addActionListener(this);
        maskMenu.add(unsharpmask);
        
        menuBar.add(fMenu);
        
        JMenuItem FFT = new JMenuItem("Forward Transform");
        FFT.addActionListener(this);
        fMenu.add(FFT);
        
        JMenuItem inversefft = new JMenuItem("Inverse Transform");
        inversefft.addActionListener(this);
        fMenu.add(inversefft);
        
        fMenu.addSeparator();
        
        JMenuItem bwnotch = new JCheckBoxMenuItem("Butterworth Notch", false);
        bwnotch.addActionListener(this);
        fMenu.add(bwnotch);
        
        fMenu.addSeparator();
        
        JMenu idealMenu = new JMenu("Ideal");
        idealMenu.addActionListener(this);
        fMenu.add(idealMenu);
        
        JMenuItem ideal_flowpass = new JMenuItem("Ideal Low Pass");
        ideal_flowpass.addActionListener(this);
        idealMenu.add(ideal_flowpass);
        
        JMenuItem ideal_fhighpass = new JMenuItem("Ideal High Pass");
        ideal_fhighpass.addActionListener(this);
        idealMenu.add(ideal_fhighpass);
        
        JMenu bwMenu = new JMenu("Butterworth");
        bwMenu.addActionListener(this);
        fMenu.add(bwMenu);
        
        JMenuItem butterworth_lowpass = new JMenuItem("Butterworth Low Pass");
        butterworth_lowpass.addActionListener(this);
        bwMenu.add(butterworth_lowpass);
        
        JMenuItem butterworth_highpass = new JMenuItem("Butterworth High Pass");
        butterworth_highpass.addActionListener(this);
        bwMenu.add(butterworth_highpass);
        
        JMenu custMenu = new JMenu("Custom");
        custMenu.addActionListener(this);
        fMenu.add(custMenu);
        
        JMenuItem customfilter1 = new JMenuItem("Custom Filter 1");
        customfilter1.addActionListener(this);
        custMenu.add(customfilter1);
        
        JMenuItem customfilter2 = new JMenuItem("Custom Filter 2");
        customfilter2.addActionListener(this);
        custMenu.add(customfilter2);
        
        JMenuItem nnscale = new JMenuItem("Nearest Neighbour Scale");
        nnscale.addActionListener(this);
        fMenu.add(nnscale);
        
        JMenuItem bscale = new JMenuItem("Bilinear Scale");
        bscale.addActionListener(this);
        fMenu.add(bscale);
        
        JMenuItem rotate = new JMenuItem("Rotate");
        rotate.addActionListener(this);
        fMenu.add(rotate);
        
        JMenuItem transform = new JMenuItem("Warp Transform");
        transform.addActionListener(this);
        fMenu.add(transform);
        
        menuBar.add(mMenu);
        
        JMenuItem gscale = new JCheckBoxMenuItem("Grayscale", false);
        gscale.addActionListener(this);
        mMenu.add(gscale);
        
        mMenu.addSeparator();
        
        JMenu eroMenu = new JMenu("Erosions");
        eroMenu.addActionListener(this);
        mMenu.add(eroMenu);
        
        JMenu dilMenu = new JMenu("Dilations");
        dilMenu.addActionListener(this);
        mMenu.add(dilMenu);
        
        JMenuItem erosion = new JMenuItem("Erosion");
        erosion.addActionListener(this);
        eroMenu.add(erosion);
        
        JMenuItem herosion = new JMenuItem("Horizontal Erosion");
        herosion.addActionListener(this);
        eroMenu.add(herosion);
        
        JMenuItem verosion = new JMenuItem("Vertical Erosion");
        verosion.addActionListener(this);
        eroMenu.add(verosion);
        
        JMenuItem dilation = new JMenuItem("Dilation");
        dilation.addActionListener(this);
        dilMenu.add(dilation);
        
        JMenuItem hdilation = new JMenuItem("Horizontal Dilation");
        hdilation.addActionListener(this);
        dilMenu.add(hdilation);
        
        JMenuItem vdilation = new JMenuItem("Vertical Dilation");
        vdilation.addActionListener(this);
        dilMenu.add(vdilation);
        
        mMenu.addSeparator();
        
        JMenuItem opening = new JMenuItem("Opening");
        opening.addActionListener(this);
        mMenu.add(opening);
        
        JMenuItem closing = new JMenuItem("Closing");
        closing.addActionListener(this);
        mMenu.add(closing);
        
        JMenuItem contourOutline = new JMenuItem("Contour Outline");
        contourOutline.addActionListener(this);
        mMenu.add(contourOutline);
        
        mMenu.addSeparator();
        
        JMenuItem gradient = new JMenuItem("Gradient");
        gradient.addActionListener(this);
        mMenu.add(gradient);
        
        JMenuItem smoothing = new JMenuItem("Smoothing");
        smoothing.addActionListener(this);
        mMenu.add(smoothing);
        
        JMenuItem topHat = new JMenuItem("Top Hat");
        topHat.addActionListener(this);
        mMenu.add(topHat);
        
        JMenuItem well = new JMenuItem("Well");
        well.addActionListener(this);
        mMenu.add(well);
        
        JMenuItem convexHull = new JMenuItem("Convex Hull");
        convexHull.addActionListener(this);
        mMenu.add(convexHull);
        
        JMenuItem thinning= new JMenuItem("Thinning");
        thinning.addActionListener(this);
        mMenu.add(thinning);
        
        JMenuItem labeling= new JMenuItem("Labeling");
        labeling.addActionListener(this);
        mMenu.add(labeling);
        
        JMenuItem identify= new JMenuItem("Object Identification");
        identify.addActionListener(this);
        mMenu.add(identify);
        

        // set up desktop
        theDesktop = new JDesktopPane();
        getContentPane().add(theDesktop);

        setSize(1024, 768);
        setVisible(true);

    } // end constructor


    // execute application
    public static void main(String args[]) throws Exception {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("Unable to load native look and feel");
        }

        IP_Handout2012 app = new IP_Handout2012();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();

       if (event.equals("Load Image"))           {          doFileChooser();         }
       else if (event.equals("Exit"))            {          System.exit(0);          }
       else if (img != null)
       {
            if (event.equals("Save Image"))      {          doFileSave();            }
       else if (event.equals("Save Image As"))   {          doFileSaveAs();          }
       else if (event.equals("Mirror on X"))     {          img.mirror_x();          }
       else if (event.equals("Mirror on Y"))     {          img.mirror_y();          }
       else if (event.equals("Rotate Left"))     {          img.rotate_left();       }
       else if (event.equals("Rotate Right"))    {          img.rotate_right();      }
       else if (event.equals("Simple BCE"))      {          img.BCE();               }
       else if (event.equals("Iso Contour Highlighting")){  img.isoBCE();            }
       else if (event.equals("Brightness Slicing"))      {  img.bsBCE();             }
       else if (event.equals("Contrast Stretch")){          img.contrastStretch();   }
       else if (event.equals("Linear Transform")){          img.linearTransform();   }
       else if (event.equals("Gamma Correction")){          img.gammaCorrection();   }
       else if (event.equals("Histogram Equalization")){    img.histoEqual();        }
       else if (event.equals("Add Histogram"))   {          doAddHisto();            }
       else if (event.equals("Brightness Shift")){          img.change();            }
       else if (event.equals("Negative"))        {          img.negative();          }
       else if (event.equals("Posterize"))       {          img.posterize();         }
       else if (event.equals("Bit Clipping"))    {          img.bitClip();           }
       else if (event.equals("Parabolic"))       {          img.parabolic();         }
       else if (event.equals("Keep Pixel bit value")) {     img.bit_planes(1);       }
       else if (event.equals("Black and White"))      {     img.bit_planes(2);       }
       else if (event.equals("Restore Image" ))  {          img.restore_image ();    }
       else if (event.equals("Clone Image" ))    {          clone_image ();          }
       else if (event.equals("Image Info" ))     {          img.img_info ();         }
       else if (event.equals("Pixelate" ))        {          img.pixelate ();        }
       else if (event.equals("Difference" ))      {          img.difference ();      }
       else if (event.equals("Addition" ))        {          img.addition ();        }
       else if (event.equals("AND" ))             {          img.and ();             }
       else if (event.equals("OR" ))              {          img.or ();              }
       else if (event.equals("XOR" ))             {          img.xor();         }
       else if (event.equals("Alpha Blend" ))     {          img.alphablend ();      }
       else if (event.equals("Watermark" ))       {          img.watermark ();         }
       else if (event.equals("Impulse Noise" ))   {          img.impulsenoise ();         }
       else if (event.equals("Median Filter" ))   {          img.medianfilter ();         }
       else if (event.equals("Mode Filter" ))     {          img.modefilter();         }
       else if (event.equals("LOW Pass 1" ))      {          img.imagemask(0);         }
       else if (event.equals("LOW Pass 2" ))      {          img.imagemask(1);         }
       else if (event.equals("LOW Pass 3" ))      {          img.imagemask(2);         }
       else if (event.equals("HIGH Pass 1" ))     {          img.imagemask(3);         }
       else if (event.equals("HIGH Pass 2" ))     {          img.imagemask(4);         }
       else if (event.equals("Laplacian 1" ))     {          img.imagemask(5);         }
       else if (event.equals("Laplacian 2" ))     {          img.imagemask(6);         }
       else if (event.equals("Shift 1" ))         {          img.imagemask(7);         }
       else if (event.equals("Shift 2" ))         {          img.imagemask(8);         }
       else if (event.equals("Shift 3" ))         {          img.imagemask(9);         }
       else if (event.equals("Horizontal Mask" )) {          img.imagemask(10);         }
       else if (event.equals("Vertical Mask" ))   {          img.imagemask(11);         }
       else if (event.equals("Unsharp Mask" ))    {          img.unsharpmask();         }
       else if (event.equals("Forward Transform" )){         img.fft ();}
       else if (event.equals("Inverse Transform" )){         img.inversefft ();}
       else if (event.equals("Butterworth Notch" )){         img.toggle(0); }
       else if (event.equals("Ideal Low Pass" ))       {     img.ideal_fpass(0);}
       else if (event.equals("Ideal High Pass" ))      {     img.ideal_fpass(1);}
       else if (event.equals("Butterworth Low Pass" )) {     img.butterworth_pass(0);}
       else if (event.equals("Butterworth High Pass" )){     img.butterworth_pass(1);}
       else if (event.equals("Custom Filter 1" )) {          img.customfilter1(20);}
       else if (event.equals("Custom Filter 2" )) {          img.customfilter2();}
       else if (event.equals("Nearest Neighbour Scale" )) {  img.scale(0);}
       else if (event.equals("Bilinear Scale" ))          {  img.scale(1);}
       else if (event.equals("Rotate" ))                  {  img.rotate();}
       else if (event.equals("Warp Transform"))           {  img.transform();}
       else if (event.equals("Erosion"))                  {  img.morpho(0);}
       else if (event.equals("Dilation"))                 {  img.morpho(1);}
       else if (event.equals("Horizontal Erosion"))       {  img.morpho(2);}
       else if (event.equals("Vertical Erosion"))         {  img.morpho(3);}
       else if (event.equals("Horizontal Dilation"))      {  img.morpho(4);}
       else if (event.equals("Vertical Dilation"))        {  img.morpho(5);}
       else if (event.equals("Opening"))                  {  img.opening();}
       else if (event.equals("Closing"))                  {  img.closing();}
       else if (event.equals("Contour Outline"))          {  img.contourOutline();}
       else if (event.equals("Grayscale"))                {  img.toggle(1);}
       else if (event.equals("Gradient"))                 {  img.gradient();}
       else if (event.equals("Smoothing"))                {  img.smoothing();}
       else if (event.equals("Top Hat"))                  {  img.topHat();}
       else if (event.equals("Well"))                     {  img.well();}
       else if (event.equals("Convex Hull"))              {  img.convexHull();}
       else if (event.equals("Thinning"))                 {  img.thinning();}
       else if (event.equals("Labeling"))                 {  img.labeling();}
       else if (event.equals("Object Identification"))    {  img.identify();}
        
   }
   else JOptionPane.showMessageDialog(null, "No active images","No Picture for you!", JOptionPane.INFORMATION_MESSAGE);
   this.repaint();
   IP_Handout2012.theDesktop.getSelectedFrame().setSize(img.width, img.height); 
   IP_Handout2012.theDesktop.getSelectedFrame().pack();
  }


// In the following 2 save methods it is assumed that the image to be saved is that currently
// stored in img variable - in other words the active window

public void doFileSave() {
        try{
            System.out.println("Saving File = " + img.getfname().toString());
            img.write(img.getfname().toString());
           }
       catch (Exception x) {
           JOptionPane.showMessageDialog(null, "Couldn't Save the Image", x.getMessage(), JOptionPane.OK_OPTION);
         }
       }


 public void doFileSaveAs() {
     JFileChooser jfc = new JFileChooser();
     jfc.setSelectedFile(new File(img.getfname().toString()));

            if (jfc.showOpenDialog(this) == jfc.APPROVE_OPTION) {
               try {
                System.out.println("File selected = " + jfc.getSelectedFile());
                String filenameExtension = img.getfname().substring(img.getfname().indexOf('.')+1);
                System.out.println("extension = " + filenameExtension);

                img.write(jfc.getSelectedFile().toString());
            }
        catch (Exception x) {
            JOptionPane.showMessageDialog(null, "Couldn't Save the Image", x.getMessage(), JOptionPane.OK_OPTION);
          }
        }
     }


public void doFileChooser() {
//  JFileChooser jfc = new JFileChooser("c://");
    FPreview jfc = new FPreview("/run/media/francoc/CN/School/Java/CS3060/Images");
    //FPreview jfc = new FPreview("K:\\School\\Java\\CS3060\\Images");

            try {
             //System.out.println("File selected = " + jfc.chooser.getSelectedFile());

             img = new ImagePro(jfc.chooser.getSelectedFile().toString());
            JInternalFrame frame = new JInternalFrame( img.getfname(), false, true, true, true );

            // attach panel to internal frame content pane
            Container container = frame.getContentPane();
            MyJPanel panel = new MyJPanel(img);
            container.add( panel, BorderLayout.WEST);

            frame.addInternalFrameListener(new Listener());
            img_vector.addElement(img);

            // set size internal frame to size of its contents
            frame.pack();

            //set up the image histogram display
            JInternalFrame hframe = new JInternalFrame( "Histogram of " + img.getfname(), false, true, true, true );
            Container hcontainer = hframe.getContentPane();
            MyJPanel hpanel = new MyJPanel(img, true);
            hcontainer.add( hpanel, BorderLayout.WEST );
            hframe.addInternalFrameListener(new Listener());
            hframe.pack();

            // attach internal frame to desktop and show it
            theDesktop.add( frame );
            frame.setVisible( true );

            theDesktop.add( hframe );
            hframe.setVisible( true );
            }

            catch (Exception e) {};// System.out.println("Exception in main() "+e.toString());   }
     }


public void doAddHisto() {
if (img != null){
    boolean found = false;
    JInternalFrame [] framelist = IP_Handout2012.theDesktop.getAllFrames();
     for (int i= 0; i < framelist.length; i++)
             if ((framelist[i].getTitle().indexOf("Histogram of "+ img.getfname())) != -1) { found = true; break;}
    if (!found) {
        JInternalFrame hframe = new JInternalFrame("Histogram of " +
                img.getfname(), false, true, true, true);
        Container hcontainer = hframe.getContentPane();
        MyJPanel hpanel = new MyJPanel(img, true);
        hcontainer.add(hpanel, BorderLayout.WEST);
        hframe.addInternalFrameListener(new Listener());
        hframe.pack();

        theDesktop.add(hframe);
        hframe.setVisible(true);
    } else JOptionPane.showMessageDialog(null, "Histogram is already open!","No Histogram for you!", JOptionPane.INFORMATION_MESSAGE);
}
else  JOptionPane.showMessageDialog(null, "Oops no images left!","No Histogram for you!", JOptionPane.INFORMATION_MESSAGE);
}


public void clone_image (){
ImagePro tmp_img;
     try {
         tmp_img = new ImagePro();
         tmp_img.width = img.width;
         tmp_img.height = img.height;

         tmp_img.o_width = tmp_img.width;
         tmp_img.o_height = tmp_img.height;

         tmp_img.fname = "Clone of " + img.fname;

         tmp_img.pixdata = new int[img.height][img.width];
         tmp_img.o_pixdata = new int[tmp_img.height][tmp_img.width];

         for (int row = 0; row < img.height; row++) {
             for (int col = 0; col < img.width; col++) {
                 tmp_img.pixdata[row][col] = img.pixdata[row][col];
                 tmp_img.o_pixdata[row][col] = tmp_img.pixdata[row][col];
              }
         }

         JInternalFrame frame = new JInternalFrame(tmp_img.getfname(), false, true, true, true);
         // attach panel to internal frame content pane
         Container container = frame.getContentPane();
         MyJPanel panel = new MyJPanel(tmp_img);
         container.add(panel, BorderLayout.WEST);
         frame.addInternalFrameListener(new Listener());
         img_vector.addElement(tmp_img);

         // set size internal frame to size of its contents
         frame.pack();
         theDesktop.add( frame );
         frame.setVisible( true );
         doAddHisto();
         System.out.println("Frame title = " +  frame.getTitle());
          }
          catch (Exception e) { System.out.println("Clone exception "+e.toString()); }

}

}  // end class DesktopTest
class MyJPanel extends JPanel {
   public ImagePro img;
   private boolean histo = false;
   // load image
   public MyJPanel(ImagePro tmp_img)
   {
     img = tmp_img;
   }

   public MyJPanel(ImagePro tmp_img, boolean histo)
   {
     img = tmp_img;
     this.histo = histo;
   }

   // display image on panel
   public void paintComponent( Graphics g )
   {
      super.paintComponent( g );
      if (!histo) img.draw(g,0,0);
      else g.drawImage(img.histogram(),0,0,null);
}

   // return image dimensions
   public Dimension getPreferredSize()
   {
     if (!histo)
     return new Dimension( img.getWidth(),
         img.getHeight() );
    else return new Dimension( 256,128 );
   }
}  // end class MyJPanel

class ImagePro {
    //------------------------------------------------------------------------------------------------------------
    // ImagePro: Protected Instance Data
    //------------------------------------------------------------------------------------------------------------
    protected int width;
    protected int height;
    protected int[][] pixdata;
    protected int [] freq;

    protected String fname;

    protected int o_width;
    protected int o_height;
    protected int[][] o_pixdata;

    private int grey_colors_used = 0;
    private int mode_color = 0;
    private int avg_intensity = 0;
    private int lowest_grey = 0;
    private int highest_grey = 0;
    private int max_freq_grey = 0;
    


    //--------------------------------------
    // ImagePro: Constructors
    //--------------------------------------
    public ImagePro(String filename) throws Exception {
        fname = filename;
        read(filename);
    }

    public ImagePro() throws Exception {
    }

    //--------------------------------------
    // ImagePro: Public Accessor Methods
    //--------------------------------------
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getfname() {
        return fname;
    }

//--------------------------------------
// ImagePro: Public Methods
//--------------------------------------
public void read(String filename) throws Exception {
        String filenameExtension = filename.substring(filename.indexOf('.') + 1);
        File fileImage = new File(filename);
        Iterator imageReaders = ImageIO.getImageReadersBySuffix(filenameExtension);
        ImageReader imageReader;
        if (imageReaders.hasNext()) imageReader = (ImageReader) imageReaders.next();
        else throw new IIOException("Unsupported image format");
        
        FileImageInputStream imageInputStream = new FileImageInputStream(fileImage);
        imageReader.setInput(imageInputStream);

        width = imageReader.getWidth(0);
        height = imageReader.getHeight(0);
        BufferedImage bufImage = imageReader.read(0);
        imageInputStream.close();
        WritableRaster wRaster = bufImage.getRaster();
        
        int numBands = wRaster.getNumBands();
        int bandnum = 0;

        pixdata = new int[height][width];
        float [] b_data = new float[numBands];
        
        //if the image is a simple greyscale image then the data is found in band 0
        if (numBands == 1) {        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixdata[row][col] = wRaster.getSample(col, row, 0);
            }
        }
        }
        else {            
        //if the image is a color image then ask the user which band to read Red - 0 Green- 1 Blue - 2 
        bandnum = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter which band to load (0-2) \nEnter -1 for average RGB", "Band Selector", JOptionPane.QUESTION_MESSAGE));       
           for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
              if (bandnum == -1) {   //user wants the average of RGB bands
                wRaster.getPixel(col, row, b_data);
                pixdata[row][col] = (int) ((b_data[0] + b_data[1] + b_data[2])/3);
              }    
              else    
                pixdata[row][col] = wRaster.getSample(col, row, bandnum);
              }
            }    
        }
        
        //copy info into o_variables used to restore original data
        o_width = width;
        o_height = height;
        o_pixdata = new int[height][width];
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                o_pixdata[row][col] = pixdata[row][col];
    }

public void write(String filename) throws Exception {
        String filenameExtension = filename.substring(filename.indexOf('.') + 1);
        File fileImage = new File(filename);
        Iterator imageWriters = ImageIO.getImageWritersBySuffix(filenameExtension);
        ImageWriter imageWriter;
        if (imageWriters.hasNext()) imageWriter = (ImageWriter) imageWriters.next();
        else throw new IIOException("Unsupported image format");
        FileImageOutputStream imageOutputStream = new FileImageOutputStream(fileImage);
        imageWriter.setOutput(imageOutputStream);
        BufferedImage bufImage = this.createBufferedImage();
        imageWriter.write(bufImage);
        imageOutputStream.close();
    }

public void draw(Graphics gc, int x, int y) {
        BufferedImage bufImage = createBufferedImage();
        gc.drawImage(bufImage, x, y, null);
    }


    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //             Image Processing Methods
    //
    //               Fix the methods below
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------


//-------------------------------------------------------------------
// Understand me so that you can code the other methods //
//-------------------------------------------------------------------
public void change() {
 int delta = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the brightness adjustment Value","Bright Ajustment",JOptionPane.QUESTION_MESSAGE));

      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          pixdata[row][col] += delta;
          if (pixdata[row][col] >= 255) pixdata[row][col] = 255;
          else  if (pixdata[row][col] <= 0) pixdata[row][col] = 0;
        }
    }
}


public void mirror_x() {
    int tmp = 0;
    for (int col = 0; col < width; col++) {
        for (int row = 0; row < height/2; row++){
            tmp =  pixdata[row][col];
            pixdata[row][col] = pixdata[height-1-row][col];
            pixdata[height-1-row][col] = tmp;
        }
    }
    }

public void mirror_y() {
    int tmp = 0;
    for (int row = 0; row < height; row++) {
        for (int col = 0; col < width/2; col++){
            tmp =  pixdata[row][col];
            pixdata[row][col] = pixdata[row][width-col-1];
            pixdata[row][width-1-col] = tmp;
        }
    }

    }


public void rotate_left() {
    int c = 0;
int tmp[][];
    tmp = new int[width][height];
    for(int col = 0; col<height; col++){
        for(int row = 0; row<width; row++){
            tmp[row][col] = pixdata[col][width-row-1];
        }
    }
    pixdata = tmp;
    c = height;
    height = width;
    width = c;
}

public void rotate_right() {
    int c = 0;
int tmp[][];
    tmp = new int[width][height];
    for(int row = 0; row<height; row++){
        for(int col = 0; col<width; col++){
            tmp[col][row] = pixdata[height-row-1][col];
        }
    }
    pixdata = tmp;
    c = height;
    height = width;
    width = c;
}

//custom methods to play around with, under FEATURE tab

public void pixelate(){
   int pixelate = 0;
   while(pixelate <= 0 || pixelate>255){
   pixelate = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the Pixelate Level", "Pixelate input",
                JOptionPane.QUESTION_MESSAGE));
   }
   
    for(int row = 0; row<height; row+=pixelate){
        for(int col = 0; col<width; col+=pixelate){
            int avg = 0;
            
            for(int i=0;i<pixelate;i++){
                for(int j=0;j<pixelate;j++){
                    avg += pixdata[row+i][col+j];
                }
            }
             avg = avg / (pixelate*pixelate);
            for(int i=0;i<pixelate;i++){
                for (int j=0;j<pixelate;j++){
                    pixdata[row+i][col+j] = avg;
                }
            }
        }
    }
    

}

public void difference(){
     int maxrow, maxcol; 
    String tmps = null;
    ImagePro t_image = null;   //t_image will be used to hold the pixdata from the selected image

    
  //IP_Handout2012.img_vector holds instances of the ImagePro class
  //every time a new file is loaded or an image is cloned then the reference 
  //to the new image is stored in the vector
    
    //myList is used to hold the list of image file names from data in the Vector 
    String[] myList = new String[IP_Handout2012.img_vector.size()];  
    
    //load the array Mylist with filenames
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
        myList[i] = ((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname();

    //use a convenient feature of showInputDialog method to permit selection of an 
    //image file name from the list of filenames of images active on the destop application.
    tmps = (String) JOptionPane.showInputDialog(
                      null, "Which Image do you want to subtract from the Active Image",
                      "Image Difference", JOptionPane.QUESTION_MESSAGE, null, myList, null);

    //find the correct image data from the vector and get the reference to the 
    //ImagePro instance
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
      if (((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname().equals(tmps))
      {
            t_image = (ImagePro) (IP_Handout2012.img_vector.elementAt(i));
            break;
      }

    //t_image points to the image - there access whatever data you need
    maxcol = Math.min(width, t_image.width);
    maxrow = Math.min(height, t_image.height);

    for (int row = 0; row < maxrow; row++) {
        for (int col = 0; col < maxcol; col++) {
           pixdata[row][col] = ((pixdata[row][col] - t_image.pixdata[row][col]) );
         
         //used to clamp data to the viable range of 0 - 255  
         if (pixdata[row][col] < 0) pixdata[row][col] = 0;
         else if (pixdata[row][col] > 255) pixdata[row][col] = 255;
      }
    }
    
}

public int[][] difference(int[][] tmp, int[][] tmp2){
   //pixdata = tmp - pixdata
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            tmp[row][col] = tmp[row][col] - pixdata[row][col];
        }
    }
    return tmp; 
}

public void addition(){
    
    int maxrow, maxcol; 
    String tmps = null;
    ImagePro t_image = null;   //t_image will be used to hold the pixdata from the selected image

    
  //IP_Handout2012.img_vector holds instances of the ImagePro class
  //every time a new file is loaded or an image is cloned then the reference 
  //to the new image is stored in the vector
    
    //myList is used to hold the list of image file names from data in the Vector 
    String[] myList = new String[IP_Handout2012.img_vector.size()];  
    
    //load the array Mylist with filenames
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
        myList[i] = ((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname();

    //use a convenient feature of showInputDialog method to permit selection of an 
    //image file name from the list of filenames of images active on the destop application.
    tmps = (String) JOptionPane.showInputDialog(
                      null, "Which Image do you want to Add to the Active Image",
                      "Image Addition", JOptionPane.QUESTION_MESSAGE, null, myList, null);

    //find the correct image data from the vector and get the reference to the 
    //ImagePro instance
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
      if (((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname().equals(tmps))
      {
            t_image = (ImagePro) (IP_Handout2012.img_vector.elementAt(i));
            break;
      }

    //t_image points to the image - there access whatever data you need
    maxcol = Math.min(width, t_image.width);
    maxrow = Math.min(height, t_image.height);

    for (int row = 0; row < maxrow; row++) {
        for (int col = 0; col < maxcol; col++) {
           pixdata[row][col] = ((pixdata[row][col] + t_image.pixdata[row][col]) );
         
         //used to clamp data to the viable range of 0 - 255  
         if (pixdata[row][col] < 0) pixdata[row][col] = 0;
         else if (pixdata[row][col] > 255) pixdata[row][col] = 255;
      }
    }
    
}

public void and(){
    int maxrow, maxcol; 
    String tmps = null;
    ImagePro t_image = null;   //t_image will be used to hold the pixdata from the selected image

    
  //IP_Handout2012.img_vector holds instances of the ImagePro class
  //every time a new file is loaded or an image is cloned then the reference 
  //to the new image is stored in the vector
    
    //myList is used to hold the list of image file names from data in the Vector 
    String[] myList = new String[IP_Handout2012.img_vector.size()];  
    
    //load the array Mylist with filenames
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
        myList[i] = ((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname();

    //use a convenient feature of showInputDialog method to permit selection of an 
    //image file name from the list of filenames of images active on the destop application.
    tmps = (String) JOptionPane.showInputDialog(
                      null, "Which Image do you want to And to the Active Image",
                      "Image 'And'", JOptionPane.QUESTION_MESSAGE, null, myList, null);

    //find the correct image data from the vector and get the reference to the 
    //ImagePro instance
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
      if (((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname().equals(tmps))
      {
            t_image = (ImagePro) (IP_Handout2012.img_vector.elementAt(i));
            break;
      }

    //t_image points to the image - there access whatever data you need
    maxcol = Math.min(width, t_image.width);
    maxrow = Math.min(height, t_image.height);

    for (int row = 0; row < maxrow; row++) {
        for (int col = 0; col < maxcol; col++) {
           pixdata[row][col] = ((pixdata[row][col] & t_image.pixdata[row][col]) );
         
         //used to clamp data to the viable range of 0 - 255  
         if (pixdata[row][col] < 0) pixdata[row][col] = 0;
         else if (pixdata[row][col] > 255) pixdata[row][col] = 255;
      }
    }
    
}

public void or(){
    int maxrow, maxcol; 
    String tmps = null;
    ImagePro t_image = null;   //t_image will be used to hold the pixdata from the selected image

    
  //IP_Handout2012.img_vector holds instances of the ImagePro class
  //every time a new file is loaded or an image is cloned then the reference 
  //to the new image is stored in the vector
    
    //myList is used to hold the list of image file names from data in the Vector 
    String[] myList = new String[IP_Handout2012.img_vector.size()];  
    
    //load the array Mylist with filenames
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
        myList[i] = ((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname();

    //use a convenient feature of showInputDialog method to permit selection of an 
    //image file name from the list of filenames of images active on the destop application.
    tmps = (String) JOptionPane.showInputDialog(
                      null, "Which Image do you want to OR to the Active Image",
                      "Image 'OR'", JOptionPane.QUESTION_MESSAGE, null, myList, null);

    //find the correct image data from the vector and get the reference to the 
    //ImagePro instance
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
      if (((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname().equals(tmps))
      {
            t_image = (ImagePro) (IP_Handout2012.img_vector.elementAt(i));
            break;
      }

    //t_image points to the image - there access whatever data you need
    maxcol = Math.min(width, t_image.width);
    maxrow = Math.min(height, t_image.height);

    for (int row = 0; row < maxrow; row++) {
        for (int col = 0; col < maxcol; col++) {
           pixdata[row][col] = ((pixdata[row][col] | t_image.pixdata[row][col]) );
         
         //used to clamp data to the viable range of 0 - 255  
         if (pixdata[row][col] < 0) pixdata[row][col] = 0;
         else if (pixdata[row][col] > 255) pixdata[row][col] = 255;
      }
    }
    
}

public void xor(){
    int maxrow, maxcol; 
    String tmps = null;
    ImagePro t_image = null;   //t_image will be used to hold the pixdata from the selected image

    
  //IP_Handout2012.img_vector holds instances of the ImagePro class
  //every time a new file is loaded or an image is cloned then the reference 
  //to the new image is stored in the vector
    
    //myList is used to hold the list of image file names from data in the Vector 
    String[] myList = new String[IP_Handout2012.img_vector.size()];  
    
    //load the array Mylist with filenames
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
        myList[i] = ((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname();

    //use a convenient feature of showInputDialog method to permit selection of an 
    //image file name from the list of filenames of images active on the destop application.
    tmps = (String) JOptionPane.showInputDialog(
                      null, "Which Image do you want to XOR to the Active Image",
                      "Image 'XOR'", JOptionPane.QUESTION_MESSAGE, null, myList, null);

    //find the correct image data from the vector and get the reference to the 
    //ImagePro instance
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
      if (((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname().equals(tmps))
      {
            t_image = (ImagePro) (IP_Handout2012.img_vector.elementAt(i));
            break;
      }

    //t_image points to the image - there access whatever data you need
    maxcol = Math.min(width, t_image.width);
    maxrow = Math.min(height, t_image.height);

    for (int row = 0; row < maxrow; row++) {
        for (int col = 0; col < maxcol; col++) {
           pixdata[row][col] = ((pixdata[row][col] ^ t_image.pixdata[row][col]) );
         
         //used to clamp data to the viable range of 0 - 255  
         if (pixdata[row][col] < 0) pixdata[row][col] = 0;
         else if (pixdata[row][col] > 255) pixdata[row][col] = 255;
      }
    }
    
}

public void alphablend(){
    int maxrow, maxcol; 
    String tmps = null;
    ImagePro t_image = null;   //t_image will be used to hold the pixdata from the selected image

    
  //IP_Handout2012.img_vector holds instances of the ImagePro class
  //every time a new file is loaded or an image is cloned then the reference 
  //to the new image is stored in the vector
    
    //myList is used to hold the list of image file names from data in the Vector 
    String[] myList = new String[IP_Handout2012.img_vector.size()];  
    
    //load the array Mylist with filenames
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
        myList[i] = ((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname();

    //use a convenient feature of showInputDialog method to permit selection of an 
    //image file name from the list of filenames of images active on the destop application.
    tmps = (String) JOptionPane.showInputDialog(
                      null, "Which Image do you want to Alpha Blend to the Active Image",
                      "Image Alpha Blend", JOptionPane.QUESTION_MESSAGE, null, myList, null);

    //find the correct image data from the vector and get the reference to the 
    //ImagePro instance
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
      if (((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname().equals(tmps))
      {
            t_image = (ImagePro) (IP_Handout2012.img_vector.elementAt(i));
            break;
      }
    
    double input = Double.parseDouble(JOptionPane.showInputDialog(null,
                "Enter the Alpha Value of input image(0.0-1.0)", "Alpha input",
                JOptionPane.QUESTION_MESSAGE));

    //t_image points to the image - there access whatever data you need
    maxcol = Math.min(width, t_image.width);
    maxrow = Math.min(height, t_image.height);

    for (int row = 0; row < maxrow; row++) {
        for (int col = 0; col < maxcol; col++) {
           pixdata[row][col] = (int)((pixdata[row][col]*(1.0-input) + t_image.pixdata[row][col]*input) );
         
         //used to clamp data to the viable range of 0 - 255  
         if (pixdata[row][col] < 0) pixdata[row][col] = 0;
         else if (pixdata[row][col] > 255) pixdata[row][col] = 255;
      }
        
    }    
}

public void watermark(){//watermark
    int maxrow, maxcol; 
    String tmps = null;
    ImagePro t_image = null;   //t_image will be used to hold the pixdata from the selected image
   
    String[] myList = new String[IP_Handout2012.img_vector.size()];  
    
    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
        myList[i] = ((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname();


    tmps = (String) JOptionPane.showInputDialog(
                      null, "Which Image do you want to Insert to the Active Image",
                      "Image Bit Plane Insert", JOptionPane.QUESTION_MESSAGE, null, myList, null);


    for (int i = 0; i < IP_Handout2012.img_vector.size(); i++)
      if (((ImagePro) (IP_Handout2012.img_vector.elementAt(i))).getfname().equals(tmps))
      {
            t_image = (ImagePro) (IP_Handout2012.img_vector.elementAt(i));
            break;
      }
    
    int bitchoice = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the Bit Plane you wish to insert at", "BP input",
                JOptionPane.QUESTION_MESSAGE));
    
    int bitval = 0;
    if(bitchoice == 0) bitval = 1;
    if(bitchoice == 1) bitval = 2;
    if(bitchoice == 2) bitval = 4;
    if(bitchoice == 3) bitval = 8;
    if(bitchoice == 4) bitval = 16;
    if(bitchoice == 5) bitval = 32;
    if(bitchoice == 6) bitval = 64;
    if(bitchoice == 7) bitval = 128;

    //t_image points to the image - there access whatever data you need
    maxcol = Math.min(width, t_image.width);
    maxrow = Math.min(height, t_image.height);

    for (int row = 0; row < maxrow; row++) {
        for (int col = 0; col < maxcol; col++) {
            if((t_image.pixdata[row][col]&bitval) != 0) pixdata[row][col] = pixdata[row][col]|bitval;
            else pixdata[row][col] = pixdata[row][col]&(255-bitval);
           //pixdata[row][col] = pixdata[row][col] + t_image.pixdata[row][col]& bitval;
         
         //used to clamp data to the viable range of 0 - 255  
         if (pixdata[row][col] < 0) pixdata[row][col] = 0;
         else if (pixdata[row][col] > 255) pixdata[row][col] = 255;
      }
        
    }  
    
}

public void impulsenoise(){//impulse noise
    
    double noise = Double.parseDouble(JOptionPane.showInputDialog(null,
                "Enter the percent noise you wish to generate (0.0 - 1.0)", "Impulse Noise",
                JOptionPane.QUESTION_MESSAGE));
    double percent = Double.parseDouble(JOptionPane.showInputDialog(null,
                "Enter the percent of white for black and white noise(0.0 - 1.0)", "Salt/Pepper Input",
                JOptionPane.QUESTION_MESSAGE));
    
   for(int row=0;row<height;row++){
       for(int col=0;col<width;col++){
           if(Math.random() < noise){
               if (Math.random() < percent) pixdata[col][row] = 255; 
               else pixdata[col][row] = 0;
           }
       }
   } 
    
    
    
}

public void medianfilter(){
   //median filter
       
    int size = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the median filter size (3/5/7)", "Median Filter input",
                JOptionPane.QUESTION_MESSAGE));
    
    int tmp[][] = new int[height][width];
    
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            int array[] = new int[size*size];
            int count = 0;
            for(int i=row-size/2;i<=row+size/2;i++){
                for(int j=col-size/2;j<=col+size/2;j++){
                    try{
                        array[count] = pixdata[i][j];
                        count++;
                    }catch (ArrayIndexOutOfBoundsException e){}
                }
            }
            Arrays.sort(array, 0, count);
            tmp[row][col] = array[(count)/2];
        }
    }
    
    pixdata = tmp;
    
    
}

public void modefilter(){
   //mode filter
    int size = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the mode filter size (3 or 5)", "Mode Filter input",
                JOptionPane.QUESTION_MESSAGE));
     int tmp[][] = new int[height][width];
    
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            int freqlist[] = new int[256];
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    try{
                        freqlist[pixdata[row+i][col+j]] += 1;
                    }catch (ArrayIndexOutOfBoundsException e){}
                }
            }
            int mode = freqlist[0];
            int modeval = 0;
            for(int k=0;k<255;k++){
                if(freqlist[k]>mode){ mode = freqlist[k]; modeval = k;}
            }
            tmp[row][col] = modeval;
        }
    }    
    pixdata = tmp;
  
}

public void imagemask(int choice){
    
    //implement unsharp mask procedure
    
    int mask0[][] = {{1,1,1},{1,1,1},{1,1,1}};
    int mask1[][] = {{1,1,1},{1,4,1},{1,1,1}};
    int mask2[][] = {{1,2,1},{2,4,2},{1,2,1}};
    int mask3[][] = {{-1,-1,-1},{-1,9,-1},{-1,-1,-1}};
    int mask4[][] = {{1,-2,1},{-2,5,-2},{1,-2,1}};
    int mask5[][] = {{0,-1,0},{-1,4,-1},{0,-1,0}};
    int mask6[][] = {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
    int mask7[][] = {{0,0,0},{-1,1,0},{0,0,0}};
    int mask8[][] = {{0,-1,0},{0,1,0},{0,0,0}};
    int mask9[][] = {{-1,0,0},{0,1,0},{0,0,0}};
    int mask10[][] = {{-1,0,1},{-2,0,2},{-1,0,1}};
    int mask11[][] = {{-1,-2,-1},{0,0,0},{1,2,1}};
    int mask[][];
      
    
    if      (choice == 0){ mask = mask0;}
    else if (choice == 1){ mask = mask1;}
    else if (choice == 2){ mask = mask2;}
    else if (choice == 3){ mask = mask3;}
    else if (choice == 4){ mask = mask4;}
    else if (choice == 5){ mask = mask5;}
    else if (choice == 6){ mask = mask6;}
    else if (choice == 7){ mask = mask7;}
    else if (choice == 8){ mask = mask8;}
    else if (choice == 9){ mask = mask9;}
    else if (choice == 10){ mask = mask10;}
    else                 { mask = mask11;}
    
    /* * /
    if (choice == 5 || choice  == 6){
       //ask user if they want original + laplacian or brightness scale
       int size = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Brightness scale(0)",
                JOptionPane.QUESTION_MESSAGE));
    }
  /* */
    
    
    int tmp[][] = new int[height][width];
    int masksum = 0;
    
    for(int i=0;i<mask.length;i++){
        for(int j=0;j<mask[0].length;j++){
            masksum+=Math.abs(mask[i][j]);
        }
    }
    
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            int result = 0;
            
            for(int i=-1;i<=1;i++){
                for(int j=-1;j<=1;j++){
                    try{
                    result += (pixdata[row+i][col+j] * mask[i+1][j+1]);
                    }catch (ArrayIndexOutOfBoundsException e){}
                }
            }
            
            if(choice < 3){tmp[row][col] = result/masksum;} //low pass 1,2,3
            else if(choice == 3 || choice ==4){             // high pass 1,2
                    tmp[row][col] = result;
                    if(tmp[row][col] > 255) tmp[row][col] = 255;
                    else if (tmp[row][col] < 0) tmp[row][col] = 0;
            }
            
            else if (choice == 5 || choice == 6){            //laplacian 1,2
                if (result<-255) result = -255;
                else if (result>255) result = 255;
                result += 255;
                result /= 2;
                tmp[row][col] = result;
            }
            
            else if (choice >= 7){
                tmp[row][col] = result;
            }
            
        }
        
    }
    pixdata = tmp;
    
    
}

public void unsharpmask() {
    //original + edge original
    int tmp[][];
    tmp = pixdata;
    imagemask(0);
    pixdata = difference(tmp, pixdata);  
}

boolean bwbool; //for bw checkbox
boolean gscale; //for grayscale checkbox

public void toggle(int choice){
         if(choice == 0)bwbool = !bwbool;
    else if(choice == 1)gscale = !gscale;
}

public void fft(){
//put this in a menu handler
s = new Spectrum(pixdata);
s.forwardFFT();
pixdata = s.fourierSpectrum();
//pixdata will now contain the Spectrum of the image
height=width=s.getSize();
IP_Handout2012.theDesktop.getSelectedFrame().pack(); 
IP_Handout2012.theDesktop.getSelectedFrame().getContentPane().addMouseListener(
 new MouseAdapter(){
 public void mousePressed(MouseEvent e){
    ideal_notch(bwbool ,e.getX(),e.getY());
    System.out.println(e.getPoint());
 }
 });
}

public void inversefft(){
    //assume that the data in ‘s’ has been transformed with forwardFFT and filtered.
s.inverseFFT();
pixdata = s.getRealDataClamped();
height=width=s.getSize();
IP_Handout2012.theDesktop.getSelectedFrame().pack();
}

 Spectrum s; //make this an instance variable

public void ideal_fpass(int choice){
    Filter f = s.getCompatableFilter();
    
    int radius = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the radius",
                JOptionPane.QUESTION_MESSAGE));
    
    for(int i=-height/2;i<height/2;i++){
        for(int j=-width/2;j<width/2;j++){
            if(choice == 0){
                if(Math.sqrt((i*i + j*j))<= radius){f.setUV(i, j, new Complex(1,0));}
                else f.setUV(i, j, new Complex(0,0));
            }
            else{
                if(Math.sqrt((i*i + j*j))<= radius){f.setUV(i, j, new Complex(0,0));}
                else f.setUV(i, j, new Complex(1,0));
            }
        }
    }
    
    //f.setUV(0,0, new Complex(0,0)); //0 will darken, 
    //2 will brighten center pixel. center pixel represents average itnensity
        try {
            s.convolve(f);//this may throw an Exception. Use a try/catch.
        } catch (Exception ex) {
            //Logger.getLogger(ImagePro.class.getName()).log(Level.SEVERE, null, ex);
        }
pixdata = s.fourierSpectrum();
//pixdata will now contain the filtered spectrum
height=width=s.getSize();
IP_Handout2012.theDesktop.getSelectedFrame().pack();
   
}

public void butterworth_pass(int choice){
    Filter f = s.getCompatableFilter();
    double dist;
    float c;
    
    int radius = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the radius",
                JOptionPane.QUESTION_MESSAGE));
    
    for(int i=-height/2;i<height/2;i++){
        for(int j=-width/2;j<width/2;j++){
            dist = Math.sqrt(i*i + j*j);
            if(choice==0) c = (float)(1.0/(1 + Math.pow(dist/radius,4))); //low pass
            else c = (float)(1.0/(1 + Math.pow(radius/dist,4)));          //high pass
            f.setUV(i, j, new Complex(c,0));
        }
    }
      
        try {
            s.convolve(f);//this may throw an Exception. Use a try/catch.
        } catch (Exception ex) {
        }
pixdata = s.fourierSpectrum();
//pixdata will now contain the filtered spectrum
height=width=s.getSize();
IP_Handout2012.theDesktop.getSelectedFrame().pack(); 
}

public void ideal_notch(boolean bw, int x, int y){
    Filter f = s.getCompatableFilter();
    x -= width/2;
    y -= height/2;
    for(int i=-5;i<+5;i++){
        for(int j=-5;j<+5;j++){
            double dist = Math.sqrt((i*i)+(j*j));
            if(dist <= 4){
                if(!bw){
                    f.setUV(x+i,y+j, new Complex(0,0));
                    f.setUV(-x+i,-y+j, new Complex(0,0));
                }
                else{
                    f.setUV(x+i,y+j, new Complex((float)(1/(1+Math.pow(4/dist,4))),0));
                    f.setUV(-x+i,-y+j, new Complex((float)(1/(1+Math.pow(4/dist,4))),0));
                }
            }
        }
    } 
    
        try {
            s.convolve(f);//this may throw an Exception. Use a try/catch.
        } catch (Exception ex) {
            //Logger.getLogger(ImagePro.class.getName()).log(Level.SEVERE, null, ex);
        }
pixdata = s.fourierSpectrum();
//pixdata will now contain the filtered spectrum
height=width=s.getSize();
IP_Handout2012.theDesktop.getSelectedFrame().pack();
   
}

public void butterworth_notch(int x, int y){
    Filter f = s.getCompatableFilter();
    x -= 128;
    y -= 128;
    
    
    
    for(int i=-5;i<+5;i++){
        for(int j=-5;j<+5;j++){
            double dist = Math.sqrt((i*i)+(j*j));
            if(dist <= 4){
                f.setUV(x+i,y+j, new Complex((float)(1/(1+Math.pow(4/dist,4))),0));
                f.setUV(-x+i,-y+j, new Complex((float)(1/(1+Math.pow(4/dist,4))),0));
                
            }
        }
    } 
    
        try {
            s.convolve(f);//this may throw an Exception. Use a try/catch.
        } catch (Exception ex) {
            //Logger.getLogger(ImagePro.class.getName()).log(Level.SEVERE, null, ex);
        }
pixdata = s.fourierSpectrum();
//pixdata will now contain the filtered spectrum
height=width=s.getSize();
IP_Handout2012.theDesktop.getSelectedFrame().pack();
   
}

public void customfilter1(int radius){
    Filter f = s.getCompatableFilter();
    //Complex c = new Complex();
    int[][] d = s.getRealData();
    
    for(int i=-height/2;i<height/2;i++){
        for(int j=-width/2;j<width/2;j++){
            //c = f.getUV(i,j);
            //System.out.println(d[i+128][j+128]);
            if(Math.sqrt(i*i + j*j)> radius && d[i+128][j+128]>3000){f.setUV(i, j, new Complex(0,0));}
            else f.setUV(i, j, new Complex(1,0));
            //f.setUV(i, j, new Complex(0,0)); //edge detection?
        }
    }
    
    
        try {
            s.convolve(f);//this may throw an Exception. Use a try/catch.
        } catch (Exception ex) {
            //Logger.getLogger(ImagePro.class.getName()).log(Level.SEVERE, null, ex);
        }
pixdata = s.fourierSpectrum();
//pixdata will now contain the filtered spectrum
height=width=s.getSize();
IP_Handout2012.theDesktop.getSelectedFrame().pack();
   
}

public void scale(int choice){
    float scale = Float.parseFloat(JOptionPane.showInputDialog(null,
                "Enter the scale",
                JOptionPane.QUESTION_MESSAGE));
    
    int theight = (int)(height*scale);
    int twidth = (int)(width*scale);
    int tmp[][] = new int[theight][twidth];
    
    if(choice == 0){
        for(int row = 0; row<theight; row++){
            for(int col = 0; col<twidth; col++){
                tmp[row][col] = pixdata[(int)(row/scale)][(int)(col/scale)];
            }
        }
    }
    else{
       
        int x1,x2,y1,y2;
        float dx,dy;
        float val = 0;
        
       for(int row = 0; row<theight; row++){
            for(int col = 0; col<twidth; col++){
                
                dx = (row/scale)%1; 
                dy = (col/scale)%1;
                x1 = (int)(row/scale);
                x2 = (int)(row/scale+0.5);
                y1 = (int)(col/scale);
                y2 = (int)(col/scale+0.5);
                
                try{
                val =  (pixdata[x2][y1] - pixdata[x1][y1])*dx;
                val += (pixdata[x1][y2] - pixdata[x1][y1])*dy;
                val += (pixdata[x2][y2] + pixdata[x1][y1] - pixdata[x1][y2] - pixdata[x2][y1])*dx*dy;
                val +=  pixdata[x1][y1];
                }catch(ArrayIndexOutOfBoundsException e){}
                
                tmp[row][col] = (int)val;
            }
        }      
      
    }
    
    height = theight;
    width = twidth;
    pixdata = tmp;
    
    IP_Handout2012.theDesktop.getSelectedFrame().pack();   
}

public void rotate(){
    int degree = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the rotation",
                JOptionPane.QUESTION_MESSAGE));
    
    double radians;
    
    radians = Math.toRadians(degree);
   
    int tx1 = (int)(Math.sin(radians) * height);
    int tx2 = (int)(Math.cos(radians) * width);
    
    int ty1 = (int)(Math.sin(radians) * width);
    int ty2 = (int)(Math.cos(radians) * height);
    
    int twidth = (tx1 + tx2);
    int theight =(ty1 + ty2);
    int tmp[][] = new int[theight][twidth];
    
    int x,y;
 
    for(int row=0;row<theight;row++){
        for(int col=0;col<twidth;col++){
            try{
            x = (int) (col*Math.cos(-radians) - row*Math.sin(-radians) - tx1*Math.cos(-radians));// + ty1*Math.sin(-radians));
            y = (int) (col*Math.sin(-radians) + row*Math.cos(-radians) - tx1*Math.sin(-radians));// - ty1*Math.cos(-radians));
            tmp[row][col] = pixdata[y][x];
            }catch(ArrayIndexOutOfBoundsException e){}
        }
    }
    
    height = theight;
    width = twidth;
    pixdata = tmp;   
    
    IP_Handout2012.theDesktop.getSelectedFrame().pack(); 
}

public void transform(){
    
    //a = [10,10,100,1;200,50,2000,1;300,300,90000,1;10,250,2500,1]
    //b = [0,0;256,0;256,256;0,256]
    //ainv = inv(a)
    //ainv * b
    
    int theight = 350;
    int twidth = 350;
    
    int tmp[][] = new int[theight][twidth];
    
    double c1,c2,c3,c4,c5,c6,c7,c8;
    
    c1 =  1.3599  ;
    c2 =  0.0159  ;
    c3 = -0.0016  ;
    c4 = -13.5993 ;     
    c5 = -0.2257  ;      
    c6 =  1.0653  ;       
    c7 = -0.0001  ;       
    c8 = -8.4101  ;
    
    int x,y = 0;
            
    for(int row=0;row<theight;row++){
        for(int col=0;col<twidth;col++){
           x = (int)(col*c1 + row*c2 + col*row*c3 + c4 + 0.5);
           y = (int)(col*c5 + row*c6 + col*row*c7 + c8 + 0.5);
           try{
            tmp[row][col] = pixdata[y][x];
           }catch(Exception e){tmp[row][col] = 255;}
        }
    }
    
    height = theight;
    width = twidth;
    pixdata = tmp;   
    
    IP_Handout2012.theDesktop.getSelectedFrame().pack(); 
    //IP_Handout2012.theDesktop.getSelectedFrame().repaint();
    
}

public void morpho(int choice){
   int mask [][] = new int[3][3];
   int mask00[][] = {{255,255,255},{255,255,255},{255,255,255}};
   int mask01[][] = {{0,0,0},{0,0,0},{0,0,0}};
   int mask02[][] = {{99,99,99},{255,255,255},{99,99,99}};
   int mask03[][] = {{99,255,99},{99,255,99},{99,255,99}};
   int mask04[][] = {{99,99,99},{0,0,0},{99,99,99}};
   int mask05[][] = {{99,0,99},{99,0,99},{99,0,99}};
   int mask10[][] = {{0,0,0},{0,0,0},{0,0,0}};
   int mask11[][] = {{0,0,0},{0,0,0},{0,0,0}};
   int mask12[][] = {{99,99,99},{1,1,1},{99,99,99}};
   int mask13[][] = {{99,1,99},{99,1,99},{99,1,99}};
   int mask14[][] = {{0,0,0},{0,0,0},{0,0,0}};
   int mask15[][] = {{0,0,0},{0,0,0},{0,0,0}};
   
   
   if(!gscale){
         if(choice == 0){mask = mask00;}//erosion
    else if(choice == 1){mask = mask01;}//dilation
    else if(choice == 2){mask = mask02;}//horizontal erosion
    else if(choice == 3){mask = mask03;}//vertical erosion
    else if(choice == 4){mask = mask04;}//horizontal dilation
    else if(choice == 5){mask = mask05;}//vertical dilation
   }
   else if(gscale){
         if(choice == 0){mask = mask10;}//erosion
    else if(choice == 1){mask = mask11;}//dilation
    else if(choice == 2){mask = mask12;}//horizontal erosion    //H/V DONT WORK ATM
    else if(choice == 3){mask = mask13;}//vertical erosion
    else if(choice == 4){mask = mask14;}//horizontal dilation
    else if(choice == 5){mask = mask15;}//vertical dilation     
   }
   
   int tmp[][] = new int[height][width];
   
   for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            boolean hit = true;
            int array[] = new int[9]; 
            int count = 0;
            
            for(int i=-1;i<=1;i++){
                for(int j=-1;j<=1;j++){
                    try{
                        if(!gscale){
                            if(mask[i+1][j+1] != pixdata[row+i][col+j] && mask[i+1][j+1] != 99){hit = false;}
                        }
                        else if(gscale){
                            if(mask[i+1][j+1] != 99){
                                array[count] = mask[i+1][j+1] + pixdata[row+i][col+j];
                                count++;
                            }                         
                        }
                        
                    }catch (ArrayIndexOutOfBoundsException e){}
                }
            }
            if(!gscale){
                if (choice == 0 || choice == 2 || choice == 3){//erosions
                    if(hit){tmp[row][col] = 255;}
                    else   {tmp[row][col] = 0;}
                    }
                else if (choice == 1 || choice == 4 || choice == 5){//dilations
                    if(hit){tmp[row][col] = 0;}
                    else   {tmp[row][col] = 255;}
                    }
            }
            else if(gscale){
                Arrays.sort(array,0,count);
                if(choice == 0) tmp[row][col] = array[0];
                else if(choice == 1) tmp[row][col] = array[count-1];
            }
            
        }
    }
   
   pixdata = tmp;
   IP_Handout2012.theDesktop.getSelectedFrame().pack(); 
}



public void opening(){
    int num = Integer.parseInt(JOptionPane.showInputDialog(null,
                "How many passes?",
                JOptionPane.QUESTION_MESSAGE));
    if(num < 0) num = 0;
    
    for(int i=0; i<num; i++){
        morpho(0);
    }
    for(int i=0; i<num; i++){
        morpho(1);
    }
}

public void closing(){
    int num = Integer.parseInt(JOptionPane.showInputDialog(null,
                "How many passes?",
                JOptionPane.QUESTION_MESSAGE));
    if(num < 0) num = 0;
    
    for(int i=0; i<num; i++){
        morpho(1);
    }
    for(int i=0; i<num; i++){
        morpho(0);
    }
    
}

public void contourOutline(){
    int tmp[][];
    tmp = pixdata;
    
    morpho(0);
     pixdata = difference(tmp, pixdata);  
}

public void gradient(){
    boolean prev = gscale;
    gscale = true;
    
    int tmp1[][] = pixdata;
    int tmp2[][];
    morpho(1);
    tmp2 = pixdata;
    pixdata = tmp1;
    morpho(0); 
    pixdata = difference(tmp2, pixdata);  
    
    gscale = prev;    
}

public void smoothing(){
    boolean prev = gscale;
    gscale = true;
    
    opening();
    closing();
    
    gscale = prev;   
}

public void topHat(){
    boolean prev = gscale;
    gscale = true;
    
    int tmp1[][] = pixdata;
    opening();
    pixdata = difference(tmp1, pixdata);  
    
    gscale = prev;    
}

public void well(){
    boolean prev = gscale;
    gscale = true;
    
    int tmp1[][] = pixdata;
    int tmp2[][] = pixdata;
    closing();
    tmp2 = pixdata;
    pixdata = tmp1;
    
    pixdata = difference(tmp2, pixdata);  
    
    gscale = prev;    
    
}

public int[][] hitnmiss(int[][] se){

    //System.out.println((int)se1[0][0]-49);
    boolean change;
    int tmp1[][] = pixdata;
    
    int pass = 0;
    
   do{
    pass++;
    change = false;
    
    for(int row=0;row<height;row++){
         for(int col=0;col<width;col++){
             boolean hit = true;

             for(int i=-1;i<=1;i++){
                 for(int j=-1;j<=1;j++){
                     try{
                          if(se[i+1][j+1] != tmp1[row+i][col+j] && se[i+1][j+1] != -1){hit = false;}
                     }catch (ArrayIndexOutOfBoundsException e){hit = false;}
                 }
             }
                    
             if(hit){
                    tmp1[row][col] = 255;
                    change = true;
             }
             
         }
     }
   System.out.println("Passes: " + pass);
   }while(change);
  
   return tmp1;

}

public void convexHull(){
    int[][] se1 = {{255,-1,-1},
                   {255, 0,-1},
                   {255,-1,-1}};
    int[][] se2 = {{255,255,255},
                   {-1, 0,-1},
                   {-1,-1,-1}};
    int[][] se3 = {{-1,-1,255},
                   {-1, 0,255},
                   {-1,-1,255}};
    int[][] se4 = {{-1,-1,-1},
                   {-1, 0,-1},
                   {255,255,255}};
    
    int minx = width;
    int miny = height;
    int maxx = 0;
    int maxy = 0;
    
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            if(pixdata[row][col] == 255){
                if(row < miny) miny = row;
                if(row > maxy) maxy = row;
                if(col < minx) minx = col;
                if(col > maxx) maxx = col;
            }
        }
    }
    
    
    int[][] x1 = hitnmiss(se1);
    int[][] x2 = hitnmiss(se2);
    int[][] x3 = hitnmiss(se3);
    int[][] x4 = hitnmiss(se4);
    
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            if((x1[row][col]==255 ||
               x2[row][col]==255 ||
               x3[row][col]==255 ||
               x4[row][col]==255) && row > miny && row < maxy && col > minx && col < maxx) pixdata[row][col] = 255;
            else pixdata[row][col] = 0;
        }
        
    }
    
}

public void thinning(){
    int[][] se00 = {{ 0 , 0 , 0 },
                    {-1 ,255, -1},
                    {255,255,255}};
    
    int[][] se01 = {{ 0 ,-1 ,255},
                    { 0 ,255,255},
                    { 0 ,-1 ,255}};
    
    int[][] se02 = {{255,255,255},
                    {-1 ,255,-1 },
                    { 0 , 0 , 0 }};
    
    int[][] se03 = {{255,-1 , 0 },
                    {255,255, 0 },
                    {255,-1 , 0 }};
    
    int[][] se10 = {{-1 , 0 , 0 },
                    {255,255, 0 },
                    {255,255,-1 }};
    
    int[][] se11 = {{ 0 , 0 ,-1 },
                    { 0 ,255,255},
                    {-1 ,255,255}};
    
    int[][] se12 = {{-1 ,255,255},
                    { 0 ,255,255},
                    { 0 , 0 ,-1 }};
    
    int[][] se13 = {{255,255,-1 },
                    {255,255, 0 },
                    {-1 , 0 , 0 }};
    
    boolean change=false;
    int tmp[][] = pixdata;
    int pass = 0;
    int[][] se = se00;
    int lastchange = 0;
    
    do{
        
        if(pass > lastchange+8)change = false;
        switch(pass++%8){
            case 0: se=se00; break;
            case 1: se=se10; break;
            case 2: se=se03; break;    
            case 3: se=se13; break;
            case 4: se=se02; break;
            case 5: se=se12; break;
            case 6: se=se01; break;
            case 7: se=se11; break; 
        }
        
        for(int row=0;row<height;row++){
            for(int col=0;col<width;col++){
                boolean hit = true;
                for(int i=-1;i<=1;i++){
                    for(int j=-1;j<=1;j++){
                        try{
                            if(se[i+1][j+1] != pixdata[row+i][col+j] && se[i+1][j+1] != -1){hit = false;} 
                        }catch (ArrayIndexOutOfBoundsException e){}
                    }
                }
                if(hit){
                    tmp[row][col] = 0;
                    lastchange = pass;
                    change = true;
                }
                else tmp[row][col] = pixdata[row][col];
            }
        }
        if(change){
            for(int row2=0;row2<height;row2++){
                     for(int col2=0;col2<height;col2++){
                                pixdata[row2][col2] = tmp[row2][col2];
                     }
            }
        }
        
        System.out.println("Pass"+pass + " se" + pass%8);
    
    }while(change);
    
    //pixdata = tmp;
    IP_Handout2012.theDesktop.getSelectedFrame().pack(); 
    
}

public int[][] rotateSE(int [][] se){
    //rotates clockwise
    int[][] tmp = new int[se.length][se[0].length];
      
    for(int row=0;row<se.length;row++){
        for(int col=0;col<se[0].length;col++){
            tmp[row][col] = se[se.length-col-1][row];
        }
    }
    
    return tmp;
    
}

public void labeling(){// NOT WORKING
    
    int tmp[][] = new int[height][width];
    
    int count = 0;
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            if(pixdata[row][col] == 255) tmp[row][col] = count++;
        }
    }
    
    int[] min = new int[5];
    boolean change;
    
    do{
        change = false;
        
        for(int row=0;row<height;row++){
            for(int col=0;col<width;col++){
                try{
                min[0] = tmp[row][col];
                min[1] = tmp[row-1][col];
                min[2] = tmp[row+1][col];
                min[3] = tmp[row][col-1];
                min[4] = tmp[row][col+1];
                }catch(Exception e){}
                Arrays.sort(min);
                if(tmp[row][col] != min[0]){tmp[row][col] = min[0]; change = true;}
            }
        }
    }while(change);
    
    System.out.println("GREYS: " + grey_colors_used);
    
    
    pixdata = tmp;
    
}

public void identify(){
    IP_Handout2012.theDesktop.getSelectedFrame().pack(); 
IP_Handout2012.theDesktop.getSelectedFrame().getContentPane().addMouseListener(
 new MouseAdapter(){
 public void mousePressed(MouseEvent e) {
    System.out.println(e.getPoint());
    if(pixdata[e.getY()][e.getX()] == 0){
        int obj = 0;
        for(int y=-5;y<5;y++){
            for(int x=-5;x<5;x++){
                try{
                if(pixdata[e.getY()+y][e.getX()+x] != 0){obj = pixdata[e.getY()+y][e.getX()+x];}
                }catch(Exception ex){}
            }
        }
        if(obj != 0) try {
            identifier(obj,1);
        } catch (Exception ex) {}
        
    }
    else try {
        identifier(pixdata[e.getY()][e.getX()],1);
    } catch (Exception ex) {}  
 }
 });
    
}

public float readAdd(HashSet obj, String loc)throws Exception{
    String line;
    BufferedReader f_in = new BufferedReader(new FileReader(loc));
    while ((line = f_in.readLine()) != null) {
        obj.add(line);
    }
    f_in.close();
    
    String data[];
    
    data = (String[]) obj.toArray();
    
    float tmp = 0;
    int i;
    for(i=0;i<obj.size();i++){
        tmp += Float.parseFloat(data[i]);
    }
    return tmp/i;
           
}

public void identifier(int obj, int choice) throws IOException, Exception{
    
    
    HashSet tri_f1 = new HashSet();
    HashSet tri_f2 = new HashSet();
    HashSet tri_f3 = new HashSet();
    
    HashSet crc_f1 = new HashSet();
    HashSet crc_f2 = new HashSet();
    HashSet crc_f3 = new HashSet();
    
    HashSet sqr_f1 = new HashSet();
    HashSet sqr_f2 = new HashSet();
    HashSet sqr_f3 = new HashSet();
    
    HashSet leta_f1 = new HashSet();
    HashSet leta_f2 = new HashSet();
    HashSet leta_f3 = new HashSet();
    
    HashSet letb_f1 = new HashSet();
    HashSet letb_f2 = new HashSet();
    HashSet letb_f3 = new HashSet();
    
    HashSet letc_f1 = new HashSet();
    HashSet letc_f2 = new HashSet();
    HashSet letc_f3 = new HashSet();
    
    float triangle_f1 = 0;
    float triangle_f2 = 0;
    float triangle_f3 = 0;

    float square_f1 = 0;
    float square_f2 = 0;
    float square_f3 = 0;

    float circle_f1 = 0;
    float circle_f2 = 0;
    float circle_f3 = 0;
    
    float a_f1 = 0;
    float a_f2 = 0;
    float a_f3 = 0;
    
    float b_f1 = 0; 
    float b_f2 = 0;
    float b_f3 = 0;

    float c_f1 = 0;
    float c_f2 = 0;
    float c_f3 = 0;
    
        
    if(choice == 1){
        try{
            triangle_f1 = readAdd(tri_f1, "./identification/tri_f1.txt");
            triangle_f2 = readAdd(tri_f2, "./identification/tri_f2.txt");
            triangle_f3 = readAdd(tri_f2, "./identification/tri_f3.txt");
        }catch(Exception e){
            triangle_f1 = 0.5128205418586731f;
            triangle_f2 = 0.09919027984142303f;
        }
        
        try{
        circle_f1 = readAdd(crc_f1, "./identification/crc_f1.txt");
        circle_f2 = readAdd(crc_f2, "./identification/crc_f2.txt");
        circle_f3 = readAdd(crc_f3, "./identification/crc_f3.txt");
        }catch(Exception e){
            circle_f1 = 0.784023642539978f;
            circle_f2 = 0.14792899787425995f;
        }
        
        try{
        square_f1 = readAdd(sqr_f1, "./identification/sqr_f1.txt");
        square_f2 = readAdd(sqr_f2, "./identification/sqr_f2.txt");
        square_f3 = readAdd(sqr_f3, "./identification/sqr_f2.txt");
        }catch(Exception e){
            square_f1 = 1.0f;
            square_f2 = 0.13793103396892548f;
        }
        
        try{
        a_f1 = readAdd(leta_f1,"./identification/leta_f1.txt");
        a_f2 = readAdd(leta_f2,"./identification/leta_f2.txt");
        a_f3 = readAdd(leta_f2,"./identification/leta_f3.txt");
        }catch(Exception e){
            a_f1 = 0.4022633731365204f;  
            a_f2 = 0.19958847761154175f;
        }
        try{
        b_f1 = readAdd(letb_f1,"./identification/letb_f1.txt");
        b_f2 = readAdd(letb_f2,"./identification/letb_f2.txt");
        b_f3 = readAdd(letb_f3,"./identification/letb_f3.txt");
        }catch(Exception e){
            b_f1 = 0.5997474789619446f;  
            b_f2 = 0.28535354137420654f;
        }
        
        try{
        c_f1 = readAdd(letc_f1,"./identification/letc_f1.txt");
        c_f2 = readAdd(letc_f2,"./identification/letc_f2.txt");
        c_f3 = readAdd(letc_f3,"./identification/letc_f3.txt");
        }catch(Exception e){
            c_f1 = 0.3888888955116272f;
            c_f2 = 0.38333332538604736f;
        }
        
        System.out.println("Features loaded from file!");
        
    }

    
    
    
    int pdata[][] = new int[height][width];
    
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            pdata[row][col] = pixdata[row][col];
        }
        
    }
    
    //int obj = 0;
    int area = 0;
    int minx = width;
    int miny = height;
    int maxx = 0;
    int maxy = 0;
    
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            if(pixdata[row][col] == obj){
                area++;
                if(col > maxx) maxx = col; //bounding box
                if(col < minx) minx = col;
                if(row > maxy) maxy = row;
                if(row < miny) miny = row;
            }
            
            
        }
        
    }
    
    int bbArea = (maxx - minx+1)*(maxy-miny+1);
    System.out.println("Area = " + area);
    System.out.println("bbArea = " + bbArea);
    double f1 = (float)area / (float)bbArea;
    
    int tmp[][] = pixdata;
    
    boolean g = gscale;
    gscale = true;
    morpho(0);
    gscale = g;
    
    pixdata = difference(tmp, pixdata); 
    
    int perimeter = 0;
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            if(pixdata[row][col] == obj) perimeter++;
        } 
    }
    
    double f2 = (float)perimeter / (float)bbArea;
    
    
   for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            pixdata[row][col] = pdata[row][col];
        }
        
    }
   
  
   int vchk = 0;
   for(int row=miny; row<maxy;row++){     
           if(pixdata[row][minx+(maxx-minx)/2] == obj) vchk++;    
   }
   
   float f3 = (float)vchk / (float)(maxy - miny);
   
   System.out.println("Area / bbArea  = " + f1);
   System.out.println("Perimeter / bbArea: " + f2);
   System.out.println("Feature3: " + f3);
    
    pixdata = pdata;
    IP_Handout2012.theDesktop.getSelectedFrame().pack(); 
    
    
    String chk;
    
    if(choice == 1){
        double d2tri =  Math.sqrt((f1-triangle_f1)*(f1-triangle_f1) + (f2-triangle_f2)*(f2-triangle_f2) + (f3-triangle_f3)*(f3-triangle_f3) );
        double d2crc =  Math.sqrt((f1-circle_f1)*(f1-circle_f1) + (f2-circle_f2)*(f2-circle_f2) + (f3-circle_f3)*(f3-circle_f3));
        double d2sqr =  Math.sqrt((f1-square_f1)*(f1-square_f1) + (f2-square_f2)*(f2-square_f2) + (f3-square_f3)*(f3-square_f3));
        double d2a =  Math.sqrt((f1-a_f1)*(f1-a_f1) + (f2-a_f2)*(f2-a_f2) + (f3-a_f3)*(f3-a_f3));
        double d2b =  Math.sqrt((f1-b_f1)*(f1-b_f1) + (f2-b_f2)*(f2-b_f2) + (f3-b_f3)*(f3-b_f3));
        double d2c =  Math.sqrt((f1-c_f1)*(f1-c_f1) + (f2-c_f2)*(f2-c_f2) + (f3-c_f3)*(f3-c_f3));
    
    
        double findmin[] = {d2tri, d2crc, d2sqr, d2a, d2b, d2c};
        Arrays.sort(findmin);


        if(findmin[0] == d2tri) JOptionPane.showMessageDialog(null, "TRIANGLE");
        else if(findmin[0] == d2crc) JOptionPane.showMessageDialog(null, "CIRCLE");
        else if(findmin[0] == d2sqr) JOptionPane.showMessageDialog(null, "SQUARE");
        else if(findmin[0] == d2a) JOptionPane.showMessageDialog(null, "LETTER A");
        else if(findmin[0] == d2b) JOptionPane.showMessageDialog(null, "LETTER B");
        else if(findmin[0] == d2c) JOptionPane.showMessageDialog(null, "LETTER C");
        
        
        chk = JOptionPane.showInputDialog(null,
                "Is this correct?",
                JOptionPane.QUESTION_MESSAGE);
        
    }
    else chk = "n";
    
    
    if("n".equals(chk)){  
        
      String input = JOptionPane.showInputDialog(null,
                "What was this object?",
                JOptionPane.QUESTION_MESSAGE);
       input = input.toLowerCase();
       if("triangle".equals(input)){ 
           readWrite(tri_f1, f1, "tri_f1.txt");
           readWrite(tri_f2, f2, "tri_f2.txt");
           readWrite(tri_f3, f3, "tri_f3.txt");
       }
       else if("circle".equals(input)){ 
           readWrite(crc_f1, f1, "crc_f1.txt");
           readWrite(crc_f2, f2, "crc_f2.txt");
           readWrite(crc_f3, f3, "crc_f3.txt");
       }
       else if("square".equals(input)){
           readWrite(sqr_f1, f1, "sqr_f1.txt");
           readWrite(sqr_f2, f2, "sqr_f2.txt");
           readWrite(sqr_f3, f3, "sqr_f3.txt");
       }
       else if("a".equals(input)){
           readWrite(leta_f1, f1, "leta_f1.txt");
           readWrite(leta_f2, f2, "leta_f2.txt");
           readWrite(leta_f3, f3, "leta_f3.txt");
       }
       else if("b".equals(input)){
           readWrite(letb_f1, f1, "letb_f1.txt");
           readWrite(letb_f2, f2, "letb_f2.txt");
           readWrite(letb_f3, f3, "letb_f3.txt");
       }
       else if("c".equals(input)){
           readWrite(letc_f1, f1, "letc_f1.txt");
           readWrite(letc_f2, f2, "letc_f2.txt");
           readWrite(letc_f3, f3, "letc_f3.txt");
       }        
    }
    System.out.println("OK!");
    
    
}

public void readWrite(HashSet obj, double feature, String loc)throws Exception{
    obj.add(feature);
    PrintWriter f_out = new PrintWriter(new BufferedWriter(new FileWriter(loc, true)));
    f_out.println(feature);
    f_out.flush();
}

public void customfilter2(){
    
}


public void BCE() {
int LUT[] = new int[256];
int threshold = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the THRESHOLD Value", "BCE input",
                JOptionPane.QUESTION_MESSAGE));

                for(int i=0;i<256;i++){
                    if(i < threshold) LUT[i] = 0;
                    else LUT[i] = 255;
                }
                
                for(int row=0; row<height; row++){
                    for(int col=0; col<width; col++){
                       pixdata[row][col] = LUT[pixdata[row][col]];
                    }
                }

    }


public void bsBCE() {
 int lowthreshold = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the LOWER THRESHOLD Value", "BCE input",
                JOptionPane.QUESTION_MESSAGE));   
 int upthreshold = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the UPPER THRESHOLD Value", "BCE input",
                JOptionPane.QUESTION_MESSAGE));
 
 int LUT[] = new int[256];
 
 for(int i=0;i<256;i++){
                    if(i < lowthreshold) LUT[i] = 0;
                    else if(i > upthreshold) LUT[i] = 255;
                }
 
 for(int row=0; row<height; row++){
                    for(int col=0; col<width; col++){
                        pixdata[row][col] = LUT[pixdata[row][col]];    
                    }
                }    
}


public void img_info() {
 JOptionPane.showMessageDialog(null,
 "\nNumber of grey levels       = " +  grey_colors_used +
 "\nLowest Grey Level           = " +  lowest_grey +
 "\nHighest Grey Level          = " +  highest_grey +
 "\nAverage Image Intensity     = " +  avg_intensity +
 "\nMode Image Grey Level       = " +  mode_color +
 "\nMax freq count              = " +  max_freq_grey +
 "\n" +
 "\nImage Height                = " +  height +
 "\nImage Width                 = " +  width,
 "Image Infomation!",JOptionPane.INFORMATION_MESSAGE);
}


public BufferedImage histogram() {
int row = 0 ,col = 0;
    int maxCount = 0;
    freq = new int[256];

//computes the frequency distribution of the grey values in the image.
        for (row = 0; row < height; row++) {
            for (col = 0; col < width; col++) {
                if (pixdata[row][col]> 255) pixdata[row][col] = 255;  
                else if(pixdata[row][col] < 0) pixdata[row][col] = 0;
                if (++freq[pixdata[row][col]] > maxCount) maxCount =
                        freq[pixdata[row][col]];
            }
        }
        
  grey_colors_used = 0;    //number of unique grey levels the number of non zero freq locations
  mode_color = freq[0];    //grey level value which occured most often
  max_freq_grey = freq[0]; //the frequency of the mode_color  
  lowest_grey = -1;         //lowest grey level value in the image
  highest_grey = -1;        //highest grey level value in the image
  avg_intensity = 0;       //(total of all pixels values) / (total number of pixels)
  int sum = 0;

  max_freq_grey = maxCount;
for(int j = 0; j<freq.length; j++){
      if(freq[j] > 0) grey_colors_used += 1;
      if(freq[j] == maxCount) mode_color = j;
      if(freq[j] > 0 && lowest_grey == -1) lowest_grey = j;
      if(freq[freq.length-1-j] > 0 && highest_grey == -1) highest_grey = freq.length-1-j;
      avg_intensity += (freq[j] * j);
}
      avg_intensity = avg_intensity / (width * height);


  

  
 BufferedImage bufImage = new BufferedImage(260,128,BufferedImage.TYPE_BYTE_GRAY);
 Graphics gc = bufImage.getGraphics();
 gc.setColor(Color.white); gc.fillRect(0,0,260,128); 

 gc.setColor(Color.black);
 for (int i = 0; i < 256; i++) gc.drawLine(i+2,127,i+2,128-(int)((double)freq[i] / maxCount * 128));
 gc.setColor(Color.LIGHT_GRAY);
 gc.drawLine( 66, 0, 66, 128);   //line marker for gray scale 64 
 gc.drawLine(130, 0, 130, 128);  //line marker for gray scale 128
 gc.drawLine(194, 0, 194, 128);  //line marker for gray scale 192
 return bufImage;
 }

public void isoBCE() {
 int lowthreshold = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the LOWER THRESHOLD Value", "BCE input",
                JOptionPane.QUESTION_MESSAGE));
 int upthreshold = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the UPPER THRESHOLD Value", "BCE input",
                JOptionPane.QUESTION_MESSAGE));
 
 int LUT[] = new int[256];
 
 for(int i=0;i<255;i++){
                    if(i > lowthreshold && i < upthreshold) LUT[i] = 255;
                    else LUT[i] = i;
                }
 
 for(int row=0;row<height;row++){
     for(int col=0;col<width;col++){
         pixdata[row][col] = LUT[pixdata[row][col]];
     }
 }
 
    
}


public void bitClip() {
}

public void parabolic() {
}

 public void contrastStretch() {
     
     int LUT[] = new int[256];
     
     for(int i=0;i<255;i++){
         LUT[i] = (int) ((i - lowest_grey) * (255.0/(highest_grey-lowest_grey)));
     }
     
     for(int row=0;row<height;row++){
         for(int col=0;col<width;col++){
             pixdata[row][col] = (int) (LUT[pixdata[row][col]]);
         }
     }
     
     
    }

public void gammaCorrection(){
     double input = Double.parseDouble(JOptionPane.showInputDialog(null,
                "Enter the Gamma Value", "Gamma input",
                JOptionPane.QUESTION_MESSAGE));
     
     int LUT[] = new int[256];
     
     for(int i=0;i<=255;i++){        
         LUT[i] = (int)(255 * Math.pow((double)i/255,input));
     }
     
    for(int col=0;col<width;col++){
        for(int row=0;row<height;row++){
            pixdata[row][col] = LUT[pixdata[row][col]];
        }
    }
}

public void linearTransform() {
}

public void histoEqual() {
    int runningtotal = 0;
    double NF  = 255.0/(width*height);
    int LUT[] = new int[256];
    
    for(int i=0;i<255;i++){
        runningtotal += freq[i];
        LUT[i] = (int) (runningtotal * NF);
        if(LUT[i]>255) LUT[i] = 255;
    }
    
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            pixdata[row][col] = LUT[pixdata[row][col]];
        }
    }
    
}

public void bit_planes(int choice) {
    
    int bitchoice = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the Bit Plane", "Bit Plane input",
                JOptionPane.QUESTION_MESSAGE));
    
    int bitval = 0;
    if(bitchoice == 0) bitval = 1;
    if(bitchoice == 1) bitval = 2;
    if(bitchoice == 2) bitval = 4;
    if(bitchoice == 3) bitval = 8;
    if(bitchoice == 4) bitval = 16;
    if(bitchoice == 5) bitval = 32;
    if(bitchoice == 6) bitval = 64;
    if(bitchoice == 7) bitval = 128;
    
    //1,2,4,8,16,32,64,128,256
    for(int row=0; row<height; row++){
        for(int col=0; col<width; col++){
            if((pixdata[row][col] & (bitval)) != 0){if(choice == 2) pixdata[row][col] = 255; else;}
            else pixdata[row][col] = 0;
        }
    }
     
 }

public void negative() {
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            pixdata[row][col] = 255-1-pixdata[row][col];
        }
    }
}

public void posterize() {
    int greys = 0;
    while(greys <= 0){
        greys = Integer.parseInt(JOptionPane.showInputDialog(null,
                "Enter the number of greys", "Quantization Levels",
                JOptionPane.QUESTION_MESSAGE));
    }
   
    for(int row=0;row<height;row++){
        for(int col=0;col<width;col++){
            int hthresh = 255/greys;
            int lthresh = 0;
            for(int i=0;i<greys;i++){
                if(pixdata[row][col] <= lthresh + (hthresh - lthresh)/2){ pixdata[row][col] = lthresh; break;}
                else if(pixdata[row][col] <= hthresh) { pixdata[row][col] = hthresh; break;}
                else {lthresh = hthresh; hthresh += 255/greys;}
            }
        }
    }
}

public void restore_image() {
        for (int row = 0; row < o_height; row++) {
            for (int col = 0; col < o_width; col++) {
                pixdata[row][col] = o_pixdata[row][col];
            }
        }
        height = o_height;
        width = o_width;
        IP_Handout2012.theDesktop.getSelectedFrame().pack();
    }


  //-------------------------------------------------------------------
  // Image: Private Methods --- Don't touch me
  //-------------------------------------------------------------------
  private BufferedImage createBufferedImage() {
    BufferedImage bufImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
    WritableRaster wRaster = bufImage.getRaster();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        wRaster.setSample(col,row,0,pixdata[row][col]);
      }
    }
    return bufImage;
  }
}

class Listener implements InternalFrameListener {

public Listener()  {}

 public void internalFrameActivated (InternalFrameEvent e) {
// debug statement   
// JOptionPane.showMessageDialog(null, "Frame Activated" + (e.getInternalFrame().getTitle()) ,"FA", JOptionPane.INFORMATION_MESSAGE);

   int i;
   ImagePro  t_image;

    if (e.getInternalFrame().getTitle().startsWith("Histogram")) System.out.println("this is not an image window ignore me");
    else { // find which img it is and make it the active img
        if (!IP_Handout2012.img_vector.isEmpty()) {
            for (i = 0; i < IP_Handout2012.img_vector.size(); i++) {
                t_image = (ImagePro) IP_Handout2012.img_vector.elementAt(i);
                if (t_image.getfname().equals(e.getInternalFrame().getTitle().
                                              toString())) {
                    IP_Handout2012.img = t_image;
                    System.out.println("img is set to : " + t_image.getfname());
                    break;
                }
            }
        }
    }
}
 public void internalFrameClosed (InternalFrameEvent e) {
 //  JOptionPane.showMessageDialog(null, "Frame Closed","FC", JOptionPane.INFORMATION_MESSAGE);
 int i;
 ImagePro  t_image;

 if (e.getInternalFrame().getTitle().startsWith("Histogram"))  System.out.println("Closing a histogram window");
  else {
       for(i = 0; i < IP_Handout2012.img_vector.size(); i++){
        t_image = (ImagePro) IP_Handout2012.img_vector.elementAt(i);
        if (t_image.getfname().equals(e.getInternalFrame().getTitle().toString())) {
        IP_Handout2012.img_vector.removeElementAt(i);
        System.out.println("Just removed image :" + t_image.getfname()+ " from Img Vector");

//remove all histogram windows containing the same filename reference
        JInternalFrame [] framelist = IP_Handout2012.theDesktop.getAllFrames();
        for (i= 0; i < framelist.length; i++)
         if ((framelist[i].getTitle().indexOf("Clone")) != 0)
         if ((framelist[i].getTitle().indexOf(e.getInternalFrame().getTitle().toString())) != -1)
             framelist[i].dispose();

        if (IP_Handout2012.img_vector.size() == 0) IP_Handout2012.img = null;
        break; }
       }
 }
}
 public void internalFrameClosing (InternalFrameEvent e) {
//   JOptionPane.showMessageDialog(null, "Frame Closing","FC",JOptionPane.INFORMATION_MESSAGE);
 }
 public void internalFrameDeactivated (InternalFrameEvent e) {
 //  JOptionPane.showMessageDialog(null, "Frame Deactivated","FDA", JOptionPane.INFORMATION_MESSAGE);
  }
 public void internalFrameDeiconified (InternalFrameEvent e) {
//   JOptionPane.showMessageDialog(null,"Frame Deiconified","FDI", JOptionPane.INFORMATION_MESSAGE);

 }
 public void internalFrameIconified (InternalFrameEvent e) {
//   JOptionPane.showMessageDialog(null, "Frame Iconified","FI", JOptionPane.INFORMATION_MESSAGE);

 }
 public void internalFrameOpened (InternalFrameEvent e) {
 //  JOptionPane.showMessageDialog(null, "Frame Open","FO", JOptionPane.INFORMATION_MESSAGE);

 }
}

class FPreview extends JFrame {
        JFileChooser chooser;
        ImagePreviewer previewer = new ImagePreviewer();

 public FPreview(String location) {
     super("Accessory Components");
                 chooser  = new JFileChooser(location);
                 Container contentPane = getContentPane();
                 contentPane.setLayout(new FlowLayout());
                 setAccessoryComponent();
                 chooser.showOpenDialog(null);

        }

 private void setAccessoryComponent() {
   JPanel previewPanel = new JPanel();
   previewPanel.setLayout(new BorderLayout());
   previewPanel.add(new JLabel("Image Previewer", SwingConstants.CENTER), BorderLayout.NORTH);
   previewPanel.add(previewer, BorderLayout.CENTER);
   previewer.setPreferredSize(new Dimension(300,0));
   previewer.setBorder(BorderFactory.createEtchedBorder());
   chooser.setAccessory(previewPanel);
   new ImagePreviewerAccessoryAdapter(chooser, previewer);
}
}

class ImagePreviewerAccessoryAdapter extends Object {
        public ImagePreviewerAccessoryAdapter( JFileChooser chooser, final ImagePreviewer previewer) {
                chooser.addPropertyChangeListener( new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent e) {
                                if(e.getPropertyName().equals(
                                   JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                                        previewer.update((File)e.getNewValue());
                                }
                        }
                });
        }
}
class ImagePreviewer extends JComponent {
        private ImageIcon icon;

public void update(File file) {
                if(file==null)return;
                Dimension size = getSize();
                Insets insets = getInsets();
                icon = new ImageIcon(file.getPath());
                icon.setImage(icon.getImage().getScaledInstance(
                                                size.width - insets.left - insets.right,
                                                size.height - insets.top - insets.bottom,
                                                Image.SCALE_SMOOTH));
                if(isShowing()) {  repaint();    }
}

public void paintComponent(Graphics g) {
                Insets insets = getInsets();
                super.paintComponent(g);
                if(icon != null)   icon.paintIcon(this, g, insets.left, insets.top);
        }
}

///////////FFT

class FFT {

  private static void four1D(float [] data, int nn, int isign){
  /* Numerical Recipes  p 394  */
    float wr,wi,wpr,wpi,wtemp,theta,tempr,tempi;
    int i,j,m,n,mmax,istep;

    /*  data: is a complex array of length nn or a real array of length 2*nn
              data[i] i=0,2,4,6... is real part
              data[j] j=1,3,5,7... is imaginary part
             |--|--|--|--|--|--|--|--|---|--|--|
             |R0|I0|R1|I1|R2|I2|R3|I3|...|Rn|In|
             |--|--|--|--|--|--|--|--|---|--|--|
               0  1  2  3  4  5  6  7

        nn:   is length (must be a power of 2 (not checked for))
        isign: 1 means replace data by its discrete FFT
               -1 means replace data by its inverse FFT
    */

    n = 2*nn;
    for (j=1,i=1; i<=n; i+=2){
      if (j>i){
        tempr = data[j-1];
        tempi = data[j];
        data[j-1] = data[i-1];
        data[j] = data[i];
        data[i-1] = tempr;
        data[i] = tempi;
      }
      m = n/2;
      while ( m>= 2 && j > m) {
        j = j-m;
        m = m/2;
      }
      j = j + m;
    }
    mmax = 2;
    float PI2 = (float)(2.0 * Math.PI);
    while (n > mmax) {
      istep = 2*mmax;
      theta = PI2 / ((float)isign*(float)mmax);
      wpr = -2 * (float)Math.pow(Math.sin(0.5*theta),2);
      wpi = (float)Math.sin(theta);
      wr = 1;
      wi = 0;
      for( m = 1; m <= mmax; m+=2){
        for (i=m;i<=n;i+=istep){
          j = i+mmax;
          tempr = wr*data[j-1] - wi*data[j];
          tempi = wr * data[j] + wi * data[j-1];
          data[j-1] = data[i-1]- tempr;
          data[j] = data[i] - tempi;
          data[i-1] = data[i-1] + tempr;
          data[i] = data[i] + tempi;
        }
        wtemp = wr;
        wr = wr * wpr - wi * wpi + wr;
        wi = wi * wpr + wtemp* wpi + wi;
      }
      mmax = istep;
    }
  }

  private static void four2D(float []data2, int nn, int isign){
    int i,j;
    float [] data = new float[nn*2];
    for(j=0;j<2*nn;j+=2) { //FFT the columns
      for(i=0;i<2*nn;i+=2) {data[i]=data2[j*nn+i];data[i+1]=data2[j*nn+i+1];}
      four1D(data,nn,isign);
      for(i=0;i<2*nn;i+=2) {data2[j*nn+i]=data[i];data2[j*nn+i+1]=data[i+1];}
    }
    for(j=0;j<2*nn;j+=2) { //FFT the rows
      for(i=0;i<2*nn;i+=2) {data[i]=data2[i*nn+j];data[i+1]=data2[i*nn+j+1];}
      four1D(data,nn,isign);
      for(i=0;i<2*nn;i+=2) {data2[i*nn+j]=data[i];data2[i*nn+j+1]=data[i+1];}
    }
  }

  public static void FFT2(float [] fftData, int size, int isign){
    four2D(fftData, size, isign);
  }

  public static void FFT2(Complex [][] fftData, int size, int isign){
    float []d = new float[size*size*2];
    int i,j,k;
    k = 0;
    for(i = 0; i < size; i++)
      for(j = 0; j < size; j++){
        d[k++]=fftData[i][j].r;
        d[k++]=fftData[i][j].i;
      }
    FFT2(d,size,isign);
    k = 0;
    for(i = 0; i < size; i++)
      for(j = 0; j < size; j++){
        fftData[i][j].r=d[k++];
        fftData[i][j].i=d[k++];
      }
  }

  public static void FFT1(float [] fftData, int size, int isign){
    four1D(fftData, size, isign);
  }

  public static void FFT1(Complex [] fftData, int size, int isign){
      float [] t = new float[size*2];
      for(int i = 0; i < fftData.length; i++){
        t[i * 2] = fftData[i].r;
        t[i * 2+1] = fftData[i].i;
      }
      four1D(t, size, isign);
      for(int i = 0; i < fftData.length; i ++){
        fftData[i].r = t[i * 2];
        fftData[i].i = t[i * 2+1];
      }
  }

  public static void main(String[] args) {
    float[] fftData = {
        0, 0, 1, 0, 2, 0, 3, 0, 1, 0, 2, 0, 3, 0, 4, 0, 2, 0, 3, 0, 4, 0, 5, 0,
        3, 0, 4, 0, 5, 0, 6, 0};
    Complex[][] d = new Complex[4][4];
    //FFT fft = new FFT();
    FFT.FFT2(fftData, 4, 1);
    System.out.println("Forward Transform");
    for (int i = 0; i < fftData.length; i += 2)
      System.out.println(fftData[i] + "+" + fftData[i + 1]);
    FFT.four2D(fftData, 4, -1);
    System.out.println("Inverse Transform");
    for (int i = 0; i < fftData.length; i += 2)
      System.out.println(fftData[i] + "-" + fftData[i + 1]);
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        d[i][j] = new Complex(i, j);
    System.out.println("New Complex Data");
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++)
        System.out.print(d[i][j].toString() + " ");
      System.out.println();
    }
    FFT.FFT2(d, 4, 1);
    System.out.println("Forward Transform");
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++)
        System.out.print(d[i][j].toString() + " ");
      System.out.println();
    }
    FFT.FFT2(d, 4, -1);
    System.out.println("Inverse Transform");
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++)
        System.out.print(d[i][j].toString() + " ");
      System.out.println();
    }

  }
}
class Complex {
  public float r; //real component of complex number
  public float i; //imaginary component of complex number
  public Complex(float r, float i){
    this.r = r;
    this.i = i;
  }
  public Complex(){
    this(0,0);
  }
  public Complex(float r){
    this(r,0);
  }
  public float toPower(){
    return r*r+i*i;
  }
  public float toMagnitude(){
    return (float)Math.sqrt(r*r + i*i);
  }
  public float toPhaseAngle(){
    return (float)Math.atan2(i,r);
  }
  public String toString(){
    if(i>=0)
      return r + "+" + i + "i";
    else return r +""+ i + "i";
  }
  public Complex add(Complex a){
    Complex t = new Complex();
    t.r = r+a.r;
    t.i = i+a.i;
    return t;
  }
  public Complex sub(Complex a){
    Complex t = new Complex();
    t.r = r-a.r;
    t.i = i-a.i;
    return t;
 }
  public Complex mul(Complex a){
    //(x+yi)(u+vi) = xu-yv + uyi + xvi;
    float t1,t2;
    t1 = r*a.r-i*a.i;
    t2 = a.r*i + r*a.i;
    return new Complex(t1,t2);
  }
  public static void main(String[] args) {
    Complex a = new Complex(3,5);
    Complex [] b = new Complex[3];
    b[0] = new Complex(3,6);
    b[1] = new Complex(5,6);
    b[2] = new Complex(3,7);
    for(int k = 0; k < b.length; k++)
      System.out.println(b[k]);
    System.out.println(a.toMagnitude());
    System.out.println(a.toPower());
    System.out.println(a.toPhaseAngle());
    System.out.println(a.mul(b[1]).toString());
  }
}

class Spectrum {
  protected Complex [][] cdata;

  private int makePowerOfTwo(int d){//round up to next power of two
    if(d<=4)return 4;
    if(d<=8)return 8;
    if(d<=16)return 16;
    if(d<=32)return 32;
    if(d<=64)return 64;
    if(d<=128)return 128;
    if(d<=256)return 256;
    if(d<=512)return 512;
    if(d<=1024)return 1024;
    if(d<=2048)return 2048;
    return 4096;
  }

  private Complex[][] makeSquareMatrix(int rows, int cols){
    Complex cdata[][];
    int d;
    d = Math.max(rows,cols);
    d = makePowerOfTwo(d);
    cdata = new Complex[d][d];
    for(int i = 0; i < cdata.length; i++)
      for(int j = 0; j < cdata[0].length; j++)
        cdata[i][j]=new Complex(0);
    return cdata;
  }

  public Spectrum(int [][] data){
    cdata = makeSquareMatrix(data.length,data[0].length);
    for(int i=0; i< data.length; i++)
      for(int j = 0; j < data[0].length; j++)
        cdata[i][j] = new Complex(data[i][j]*(float)Math.pow(-1,i+j),0);
  }

  public Spectrum(Spectrum s) throws Exception {
    if (s.cdata.length != s.cdata[0].length)
      throw new Exception("matrix is not square!");
    if (! (isPowerOfTwo(s.cdata.length) && isPowerOfTwo(s.cdata[0].length)))
      throw new Exception("matrix is not a power of two!");
    cdata = new Complex[s.cdata.length][s.cdata[0].length];
    for (int i = 0; i < cdata.length; i++)
      for (int j = 0; j < cdata[0].length; j++)
        cdata[i][j] = s.cdata[i][j];
  }

  public Filter getCompatableFilter(){
    return new Filter(cdata.length);
  }

  private boolean isPowerOfTwo(int n){
    int value = 4;
    for(int i = 2; i < 16; i++){
      if(n==value)return true;
      value*=2;
    }
    return false;
  }

  public int clamp(double d){
    if(d<0)d = 0;
    if(d>255) d = 255;
    return (int)d;
  }

  public int [][] fourierSpectrum(int scale){
    int [][] data = new int[cdata.length][cdata[0].length];
    for(int i = 0; i < cdata.length; i++)
      for (int j = 0; j < cdata[0].length; j++)
        data[i][j]=clamp(scale*Math.log(1 + cdata[i][j].toMagnitude()));
    return data;
  }

  public int [][] fourierSpectrum(){
    return fourierSpectrum(20);
  }

  public int [][] getRealData(){
    int [][] data = new int[cdata.length][cdata[0].length];
    for(int i = 0; i < cdata.length; i++)
      for (int j = 0; j < cdata[0].length; j++)
        data[i][j]=(int)(cdata[i][j].r*Math.pow(-1,i+j));
    return data;
  }

  public int [][] getRealDataClamped(){
    int [][] data = new int[cdata.length][cdata[0].length];
    for(int i = 0; i < cdata.length; i++)
      for (int j = 0; j < cdata[0].length; j++)
        data[i][j]=clamp((int)(cdata[i][j].r*Math.pow(-1,i+j)));
    return data;
  }

  public int [][] getImaginaryData(){
    int [][] data = new int[cdata.length][cdata[0].length];
    for(int i = 0; i < cdata.length; i++)
      for (int j = 0; j < cdata[0].length; j++)
        data[i][j]=(int)(cdata[i][j].i*Math.pow(-1,i+j));
    return data;
  }

  public int [][] getImaginaryDataClamped(){
    int [][] data = new int[cdata.length][cdata[0].length];
    for(int i = 0; i < cdata.length; i++)
      for (int j = 0; j < cdata[0].length; j++)
        data[i][j]=clamp((int)(cdata[i][j].i*Math.pow(-1,i+j)));
    return data;
  }

  public void print(){
    if (cdata==null) return;
    for(int i = 0; i < cdata.length; i++){
    for (int j = 0; j < cdata[0].length; j++)
      System.out.print(cdata[i][j].toString() + " ");
      System.out.println();
    }
  }

  public void forwardFFT(){
    FFT.FFT2(cdata,cdata.length,1);
  }

  public void inverseFFT(){
    Complex a = new Complex(1.0f/(cdata.length*cdata.length),0);
    FFT.FFT2(cdata,cdata.length,-1);
    for(int i = 0; i < cdata.length; i++)
      for (int j = 0; j < cdata[0].length; j++)
        cdata[i][j] = cdata[i][j].mul(a);

  }

  private void convolve(Complex [][] filter)throws Exception{
    if((filter.length!=cdata.length)&&(filter[0].length!=cdata[0].length))
    throw new Exception("Filter is not the same size as the Spectrum!");
    for(int i = 0; i < cdata.length; i++)
    for (int j = 0; j < cdata[0].length; j++)
      cdata[i][j]= cdata[i][j].mul(filter[i][j]);
  }

  public void convolve(Filter f)throws Exception{
    convolve(f.getFilter());
  }

  public int getSize(){
    return cdata.length;
  }

  public static void main(String[] args) throws Exception{
    Filter f;
    int [][] d = new int[5][4];
    for(int i = 0; i < d.length; i++)
      for(int j = 0; j < d[0].length; j ++)
        d[i][j] = i+j;
    System.out.println("Original Data");
    for(int i = 0; i < d.length; i++){
      for (int j = 0; j < d[0].length; j++)
        System.out.print(d[i][j] + " ");
      System.out.println();
    }
    Spectrum s = new Spectrum(d);
    //s.print();
    s.forwardFFT();
    s.print();
    f = s.getCompatableFilter();
    s.convolve(f);
    s.print();
    s.inverseFFT();
    s.print();
    d = s.fourierSpectrum();
    System.out.println("Fourier Spectrum Data");
    for(int i = 0; i < d.length; i++){
      for (int j = 0; j < d[0].length; j++)
        System.out.print(d[i][j] + " ");
      System.out.println();
    }
    d = s.getRealData();
    System.out.println("Real Data");
    for(int i = 0; i < d.length; i++){
      for (int j = 0; j < d[0].length; j++)
        System.out.print(d[i][j] + " ");
      System.out.println();
    }
    d = s.getImaginaryData();
    System.out.println("Imaginary Data");
    for(int i = 0; i < d.length; i++){
      for (int j = 0; j < d[0].length; j++)
        System.out.print(d[i][j] + " ");
      System.out.println();
    }
  }
}

class Filter {
  protected Complex [][] filter;

  public Filter(int size){//initialize to the "identity filter"
    filter = new Complex[size][size];
    for(int i = 0; i < size; i++)
      for(int j = 0; j < size; j++)
        filter[i][j]=new Complex(1,0);
  }

  public Complex [][] getFilter(){
    return filter;
  }

  public int xToU(int x){
    return x-filter.length/2;
  }

  public int yToV(int y){
    return y-filter.length/2;
  }

  public int uToX(int u){
    return filter.length/2+u;
  }

  public int vToY(int v){
    return filter.length/2+v;
  }

  public void setUV(int u, int v, Complex c){
    filter[vToY(v)][uToX(u)] = c;
  }

  public Complex getUV(int u, int v){
    return filter[vToY(v)][uToX(u)];
  }

  public void setXY(int x, int y, Complex c){
    filter[y][x] = c;
  }

  public Complex getXY(int x, int y){
    return filter[y][x];
  }

  public int getSize(){
    return filter.length;
  }

  public static void main(String[] args) throws Exception{
    int [][] d = new int[4][4];
    for(int i = 0; i < 4; i++)
      for(int j = 0; j < 4; j ++)
        d[i][j] = i+j;

    Filter f;
    Spectrum s = new Spectrum(d);
    f = s.getCompatableFilter();
    s.forwardFFT();
    f.setUV(0,0,new Complex(0));
    s.convolve(f);
    s.inverseFFT();
    s.print();
    d=s.getRealData();
    for(int i = 0; i < d.length; i++){
      for (int j = 0; j < d[0].length; j++)
        System.out.print(d[i][j]+" ");
      System.out.println();
    }
  }
}
