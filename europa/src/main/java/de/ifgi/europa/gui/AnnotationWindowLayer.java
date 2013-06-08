/**
   Copyright 2013 Jim Jones, Matthias Pfeil and Alber Sanchez

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package de.ifgi.europa.gui;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.layers.AbstractLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.util.Logging;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * AnnotationWindowLayer is a layer container for AnnotationWindow objects. 
 * All AnnotationWindow contained in the AnnotationWindowLayer are rendered 
 * through the AnnotationWindowRenderer.
 * 
 * @author Antonio Santiago [asantiagop(at)gmail.com]
 * @see AnnotationWindow
 */
public class AnnotationWindowLayer extends AbstractLayer implements ComponentListener, Layer
{

    private final java.util.Collection<AnnotationWindow> windows = new ConcurrentLinkedQueue<AnnotationWindow>();
    private AnnotationWindowRenderer windowRenderer = new AnnotationWindowRenderer();
    private WorldWindowGLCanvas wwd = null;

    /**
     * Creates a new instance.
     * @param wwd 
     */
    public AnnotationWindowLayer(WorldWindowGLCanvas wwd)
    {
        this.wwd = wwd;
        this.wwd.addComponentListener(this);
    }

    /**
     * Adds a new AnnotationWindow to the layer.
     * @param window 
     */
    public void addWindow(AnnotationWindow window)
    {
        if (window == null)
        {
            String msg = "Widget is null";
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }

        this.windows.add(window);
    }

    /**
     * Removes the specified window from the layer.
     * @param window
     */
    public void removeWidget(AnnotationWindow window)
    {
        if (window == null)
        {
            String msg = "Widget is null";
            Logging.logger().severe(msg);
            throw new IllegalArgumentException(msg);
        }

        this.windows.remove(window);
    }

    /**
     * Returns a collection of windows.
     * @return windows
     */
    public java.util.Collection<AnnotationWindow> getWindows()
    {
        return this.windows;
    }

    @Override
    protected void doPick(DrawContext dc, java.awt.Point pickPoint)
    {
        this.windowRenderer.pick(dc, this.windows, pickPoint, this);
    }

    /**
     * Renders the windows of the layer. 
     * 
     * @param dc
     */
    @Override
    protected void doRender(DrawContext dc)
    {
        this.windowRenderer.render(dc, this.windows);
    }

    public void componentResized(ComponentEvent e)
    {
        System.out.println("componentResized");
    }

    public void componentMoved(ComponentEvent e)
    {
        System.out.println("componentMoved");
    }

    public void componentShown(ComponentEvent e)
    {
        System.out.println("componentShown");
    }

    public void componentHidden(ComponentEvent e)
    {
        System.out.println("componentHidden");
    }
}
