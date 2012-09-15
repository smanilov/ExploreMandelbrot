ExploreMandelbrot
=================

Intro
-----

ExploreMandelbrot is a personal project that I started during my exam session of May 2011. It is a sandbox for experimenting with different concepts like anti-aliasing, random-order rendering, multithreading synchronization, etc.

It is public now with the hope that it will be useful to you too.

Structure
---------

The program is implemented in Java. The code is split in three packages:

- smanilov.mandelbrot
- smanilov.mandelbrot.compute
- smanilov.mandelbrot.gui

### smanilov.mandelbrot

This package contains the entry point to the program - Mandelbrot.java. Also here you can find code files that are too immature to go to another package.

### smanilov.mandelbrot.compute

This package contains the code that does the maths. Currently there are two files: Camera.java and Computer.java. The first one is concerned with the navigation of the view of the fractal, the second one does all the rendering and thread synchronization.

### smanilov.mandelbrot.gui

This package contains the GUI elements: CanvasPanel.java, ControlPanel.java, and ColorsPanel.java. The first one is where the fractal is drawn and the one containing a progress bar. The second one contains the navigation buttons and rendering settings. The third one is for selecting colors.
