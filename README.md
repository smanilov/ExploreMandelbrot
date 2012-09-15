ExploreMandelbrot
=================

Intro
-----

ExploreMandelbrot is a personal project that I started during my exam session of May 2012. It is a sandbox for experimenting with different concepts like anti-aliasing, random-order rendering, multithreading synchronization, etc.

It is public now with the hope that it will be useful to you too.

Structure
---------

The program is implemented in Java. The code is split in three packages:

- smanilov.mandelbrot
- smanilov.mandelbrot.compute
- smanilov.mandelbrot.gui

### smanilov.mandelbrot

- Colors.java
- Mandelbrot.java

This package contains the entry point to the program - Mandelbrot.java. Also, here you can find code files that are too immature to go to another package.

### smanilov.mandelbrot.compute

- Camera.java
- Computer.java

This package contains the code that does the maths. Currently there are two files: Camera.java and Computer.java. The first one is concerned with the navigation of the view of the fractal, the second one does all the rendering and thread synchronization.

### smanilov.mandelbrot.gui

- CanvasPanel.java
- ControlPanel.java
- ColorPanel.java

This package contains the GUI elements: CanvasPanel.java, ControlPanel.java, and ColorsPanel.java. The first one is where the fractal is drawn and the one containing a progress bar. The second one contains the navigation buttons and rendering settings. The third one is for selecting colors.

Rendering
---------

- Computer.java

The way that ExploreMandelbrot renders the image is in a random-order fashion. Once a new drawing command is issued, a linked queue of points is initialized as empty, and a producer thread is started. Also, the specified number of 'shaders' are started. 

The work of the producer is to fill the queue with coordinates of random points of the canvas that have not been drawn yet. Since the queue is a linked queue, it is limited only by the amount of memory that the host computer has, but we don't want to eat all of it, so if a preset constant limit is reached, the producer will wait until the queue is reduced by the shaders.

The shaders take points from the queue and compute their color while applying anti-aliasing. After this they draw them on a shared image while locking it. When all the points are drawn the image is saved as mandelbrot.png.

Outro
-----

You can send any questions you have to stanislav.manilov at gmail dt com. 

Enjoy.