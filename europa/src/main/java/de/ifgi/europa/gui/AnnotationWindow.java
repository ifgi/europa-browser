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
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Vec4;
import gov.nasa.worldwind.render.DrawContext;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.media.opengl.GL;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

/**
 *
 * @author Antonio Santiago [asantiagop(at)gmail.com]
 */
public class AnnotationWindow extends JWindow
{

    private boolean enable = true;
    private JPanel panel = null;
    private WorldWindowGLCanvas wwd = null;
    private Point point = new Point(0, 0);
    private Position position = Position.fromDegrees(0, 0, 0);
    private Color color = Color.LIGHT_GRAY;

    public AnnotationWindow(Position position, WorldWindowGLCanvas wwd)
    {
        this.position = position;
        this.wwd = wwd;

        // Initially the JWindow is hidden.
        this.setVisible(false);

        // Set window border.
        JPanel content = (JPanel) this.getContentPane();
        content.setBorder(new LineBorder(this.color, 2));
    }

    public JPanel getPanel()
    {
        return panel;
    }

    public void setPanel(JPanel panel)
    {
        if (panel == null)
        {
            return;
        }
        this.panel = panel;

        this.getContentPane().add(panel);
        this.pack();
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public boolean isEnable()
    {
        return enable;
    }

    public void setEnable(boolean enable)
    {
        this.enable = enable;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Point getPoint()
    {
        return this.point;
    }

    /**
     * Renders the AnnotationWindow and sets it to a fixed position (upper left corner) inside the
     * WorldWindowGLCanvas.
     * @param dc draw context
     */
    public void render(DrawContext dc)
    {

        this.getSize();
        wwd.getSize();
        Point loc = wwd.getLocationOnScreen();
        
        Vec4 cartpoint = dc.getGlobe().computePointFromPosition(this.position);
        this.setVisible(true);

        dc.getView().project(cartpoint);

        this.point.x = loc.x + 5;
        this.point.y = loc.y + 5;

        // Set the window location.
        this.setLocation(this.point);
        
        // Draw triangle
        GL gl = dc.getGL();

        float[] c = this.color.getRGBComponents(null);
        gl.glColor3f(c[0], c[1], c[2]);

        gl.glLineWidth(2);

        gl.glBegin(GL.GL_TRIANGLES);
        
        gl.glEnd();
    }
}
