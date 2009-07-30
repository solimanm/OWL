/**
 * Project Wonderland
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * Sun designates this particular file as subject to the "Classpath"
 * exception as provided by Sun in the License file that accompanied
 * this code.
 */
package org.jdesktop.wonderland.modules.avatarbase.client.imi;

import imi.character.AttachmentParams;
import imi.character.CharacterParams;
import imi.character.CharacterParams.SkinnedMeshParams;
import imi.scene.PMatrix;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jdesktop.wonderland.modules.avatarbase.client.imi.WonderlandCharacterParams.ConfigElement;
import org.jdesktop.wonderland.modules.avatarbase.client.imi.WonderlandCharacterParams.GenderConfigElement;

/**
 * Avatar attributes for generating Wonderland avatars
 * @author jkaplan
 */
public class WonderlandCharacterParams implements Cloneable {
    private static final Logger logger =
            Logger.getLogger(WonderlandCharacterParams.class.getName());

    // The configuration URL used to load the preset information
    private URL configURL = null;

    // Pointers to the XML files that define the various avatar configurations
    // available.
    private static final String MALE_CONFIGS   = "resources/male-configs.xml";
    private static final String FEMALE_CONFIGS = "resources/female-configs.xml";

    public enum ConfigType {
        GENDER, HAIR, HAIR_COLOR, HEAD, SKIN_COLOR, TORSO,
        SHIRT_COLOR, JACKET, HANDS, LEGS, PANTS_COLOR, FEET,
        SHOE_COLOR
    };

    private final Map<ConfigType, List<ConfigElement>> allElements =
            new EnumMap<ConfigType, List<ConfigElement>>(ConfigType.class);
    private final Map<ConfigType, ConfigElement> elements =
            new EnumMap<ConfigType, ConfigElement>(ConfigType.class);

    // A set of key-value meta-data for the
    // The JAXB context to (un)marshall (from)to XML
    private static JAXBContext jaxbContext = null;
    static {
        try {
            jaxbContext = JAXBContext.newInstance(ConfigList.class);
        } catch (javax.xml.bind.JAXBException excp) {
            logger.log(Level.WARNING, "Error creating JAXB context", excp);
        }
    }

    public WonderlandCharacterParams(URL configURL) {
        this.configURL = configURL;
        loadConfig(configURL);
    }

    public static WonderlandCharacterParams loadMale() throws IOException {
        URL maleURL = WonderlandCharacterParams.class.getResource(MALE_CONFIGS);
        return new WonderlandCharacterParams(maleURL);
    }

    public static WonderlandCharacterParams loadFemale() throws IOException {
        URL femaleURL = WonderlandCharacterParams.class.getResource(FEMALE_CONFIGS);
        return new WonderlandCharacterParams(femaleURL);
    }

    /**
     * Returns the IMI avatar character params that correspond to the current
     * settings.
     *
     * @return A CharacterParms
     */
    @XmlTransient
    public CharacterParams getCharacterParams() {
        // Take all of the configuration elements and apply them to the params
        CharacterParams out = new CharacterParams();
        for (ConfigElement element : getElements()) {
            element.apply(out);
        }

        return out;
    }

    /**
     * Takes a set of IMI character parameters and updates the settings in this
     * set of attributes according to the presets stored in the given params.
     *
     * @param A CharacterParams
     */
    public void setCharacterParams(CharacterParams params) {
        // For all of the config type elements that use presets, take from
        // the IMI character params and set as elements in the settings here
        setAsPreset(params, ConfigType.HEAD);
        setAsPreset(params, ConfigType.HAIR);
        setAsPreset(params, ConfigType.TORSO);
        setAsPreset(params, ConfigType.JACKET);
        setAsPreset(params, ConfigType.HANDS);
        setAsPreset(params, ConfigType.LEGS);
        setAsPreset(params, ConfigType.FEET);
        setAsPreset(params, ConfigType.HANDS);

        // Set the colors
        setAsColor(params, ConfigType.HAIR_COLOR);
        setAsColor(params, ConfigType.PANTS_COLOR);
        setAsColor(params, ConfigType.SHIRT_COLOR);
        setAsColor(params, ConfigType.SHOE_COLOR);
        setAsColor(params, ConfigType.SKIN_COLOR);
    }

    /**
     * A utility routine to fetch the preset from the IMI character params meta
     * data and set the config element in this set of attributes.
     */
    private void setAsPreset(CharacterParams params, ConfigType type) {
        String presetName = params.getMetaData().get(type.toString());
        if (presetName == null) {
            logger.warning("No preset found for config type " + type);
            setElement(type, 0);
            return;
        }

        int index = setElementPreset(type, presetName);
        if (index == -1) {
            logger.warning("Preset for config type " + type + " is not in " +
                    "set of config elements " + presetName);
            setElement(type, 0);
            return;
        }
        logger.warning("For config type " + type + ", using preset " + presetName);
    }

    /**
     * A utility routine to fetch the color from the IMI character params meta
     * data and set the config element in this set of attributes.
     */
    private void setAsColor(CharacterParams params, ConfigType type) {
        String colorString = params.getMetaData().get(type.toString());
        if (colorString == null) {
            logger.warning("No color found for config type " + type);
            return;
        }

        // The color string is of the format RRR GGG BBB where the values are
        // between 0 - 255 for each color component.
        String rgb[] = colorString.split(" ");
        float r = (float)Integer.parseInt(rgb[0]) / 255.0f;
        float g = (float)Integer.parseInt(rgb[1]) / 255.0f;
        float b = (float)Integer.parseInt(rgb[2]) / 255.0f;

        logger.warning("For color type " + type + ", using color " + r + " " +
                g + " " + b);

        ColorConfigElement e = null;
        switch (type) {
            case HAIR_COLOR:
                e = new HairColorConfigElement();
                e.setRGB(r, g, b);
                break;

            case PANTS_COLOR:
                e = new PantsColorConfigElement();
                e.setRGB(r, g, b);
                break;

            case SHIRT_COLOR:
                e = new ShirtColorConfigElement();
                e.setRGB(r, g, b);
                break;

            case SHOE_COLOR:
                e = new ShoeColorConfigElement();
                e.setRGB(r, g, b);
                break;

            case SKIN_COLOR:
                e = new SkinColorConfigElement();
                e.setRGB(r, g, b);
                break;

            default:
                break;
        }

        if (e != null) {
            setElement(type, e);
        }
    }

    @XmlTransient
    public Collection<ConfigElement> getElements() {
        return elements.values();
    }

    public ConfigElement getElement(ConfigType type) {
        return elements.get(type);
    }

    /**
     * Returns the index in the list of the currently selected configuration
     * element for a particular element type.
     *
     * @param type The configuration element type
     * @return The index in the list of all elements
     */
    public int getElementIndex(ConfigType type) {
        ConfigElement ce = getElement(type);
        List<ConfigElement> el = getElements(type);
        return el.indexOf(ce);
    }

    public ConfigElement getElement(ConfigType type, int index) {
        List<ConfigElement> el = getElements(type);
        return el.get(index);
    }

    public void setElement(ConfigType type, int index) {
        List<ConfigElement> el = getElements(type);
        ConfigElement ce = el.get(index);
        elements.put(type, ce);
    }

    public void setElement(ConfigType type, ConfigElement element) {
        elements.put(type, element);
    }

    /**
     * Sets the current element preset given the configuration type and the
     * name of the present. Returns the index of the element set, or -1 if
     * the preset name did not exist.
     *
     * @param type The configuration element type
     * @param name The name given to the preset
     * @return The index of the preset, or -1 if it does not exist
     */
    public int setElementPreset(ConfigType type, String presetName) {
        // Loop through all of the elements for the type. If we found one
        // then set it and return the index.
        int i = 0;
        for (ConfigElement ce : getElements(type)) {
            if (presetName.equals(ce.getName()) == true) {
                setElement(type, ce);
                return i;
            }
            i++;
        }

        // If we've reached here, it means we did not find the preset name in
        // the configuration list, so we return -1
        return -1;
    }

    public int getElementCount(ConfigType type) {
        return getElements(type).size();
    }

    public List<ConfigElement> getElements(ConfigType type) {
        List<ConfigElement> el = allElements.get(type);
        if (el == null) {
            el = Collections.emptyList();
        }

        return el;
    }

    /**
     * Randomizes the settings for this set of character parameters.
     */
    public void randomize() {
        for (ConfigType type : ConfigType.values()) {
            List<ConfigElement> el = getElements(type);
            if (el.size() > 0) {
                int rand = (int) (Math.random() * 100) % el.size();
                setElement(type, rand);
            }
        }

        // XXX What about randomizing the colors?
    }

    /**
     * Make a deep copy of this object.
     *
     * @return A clone of this object
     */
    @Override
    public WonderlandCharacterParams clone() {
        // Create a new object, since we give it a configURL, it will re-load
        // the 'allElements' map
        WonderlandCharacterParams attributes = new WonderlandCharacterParams(configURL);

        // Make a copy of the currently-set elements
        for (ConfigType type : elements.keySet()) {
            attributes.elements.put(type, elements.get(type).clone(null));
        }

        return attributes;
    }

    protected void loadConfig(URL configURL) {
        allElements.clear();

        try {
            ConfigList config = ConfigList.decode(configURL.openStream());
            allElements.put(ConfigType.GENDER, Arrays.asList((ConfigElement[]) config.getGenders()));
            allElements.put(ConfigType.HEAD, Arrays.asList((ConfigElement[]) config.getHeads()));
            allElements.put(ConfigType.HAIR, Arrays.asList((ConfigElement[]) config.getHair()));
            allElements.put(ConfigType.TORSO, Arrays.asList((ConfigElement[]) config.getTorsos()));
            allElements.put(ConfigType.JACKET, Arrays.asList((ConfigElement[]) config.getJackets()));
            allElements.put(ConfigType.HANDS, Arrays.asList((ConfigElement[]) config.getHands()));
            allElements.put(ConfigType.LEGS, Arrays.asList((ConfigElement[]) config.getLegs()));
            allElements.put(ConfigType.FEET, Arrays.asList((ConfigElement[]) config.getFeet()));

            // load the first element of each type
            for (ConfigType type : ConfigType.values()) {
                List<ConfigElement> el = getElements(type);
                if (el.size() > 0) {
                    setElement(type, 0);
                }
            }            
        } catch (IOException ioe) {
            logger.log(Level.WARNING, "Error loading config from " +
                       configURL, ioe);
        }
    }

    public static abstract class ConfigElement {
        private String name;
        private String description;
        private URL previewImage;

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @XmlElement
        public URL getPreviewImage() {
            return previewImage;
        }

        public void setPreviewImage(URL previewImage) {
            this.previewImage = previewImage;
        }

        public abstract void apply(CharacterParams attrs);

        // compare ConfigElements by name.  Each element should have a unique
        // name.
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ConfigElement other = (ConfigElement) obj;
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + (this.name != null ? this.name.hashCode() : 0);
            return hash;
        }

        /**
         * Clones this ConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        public ConfigElement clone(ConfigElement element) {
            element.name = name;
            element.description = description;
            element.previewImage = previewImage;
            return element;
        }
    }

    public static class GenderConfigElement extends ConfigElement {

        /** Enumeration of Gender types */
        public static final int MALE  = 1;
        public static final int FEMALE = 2;

        private int gender;

        @XmlElement
        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        @Override
        public void apply(CharacterParams attrs) {
            attrs.setGender(gender).setUsePhongLightingForHead(true);
        }

        /**
         * Clones this GenderConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new GenderConfigElement();
            }
            ((GenderConfigElement)element).gender = gender;
            return super.clone(element);
        }
    }

    public static abstract class ModelConfigElement extends ConfigElement {
        private String model;

        @XmlElement
        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        /**
         * Clones this ModelConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            element = super.clone(element);
            ((ModelConfigElement)element).model = model;
            return element;
        }
    }

    public static class HeadConfigElement extends ModelConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            attrs.setHeadAttachment(getModel());
            attrs.getMetaData().put(ConfigType.HEAD.toString(), getName());
        }

        /**
         * Clones this HeadConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new HeadConfigElement();
            }
            return super.clone(element);
        }
    }

    public static abstract class ShapesConfigElement extends ModelConfigElement {
        private String[] shapes;

        @XmlElement
        public String[] getShapes() {
            return shapes;
        }

        public void setShapes(String[] shapes) {
            this.shapes = shapes;
        }

        /**
         * Clones this ShapesConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (shapes != null) {
                ((ShapesConfigElement) element).shapes = new String[shapes.length];
                for (int i = 0; i < shapes.length; i++) {
                    ((ShapesConfigElement) element).shapes[i] = shapes[i];
                }
            }
            return super.clone(element);
        }
    }

    public static class HairConfigElement extends ShapesConfigElement {
        @Override
        public void apply(CharacterParams attrs) {            
            attrs.addLoadInstruction(getModel());
            attrs.getMetaData().put(ConfigType.HAIR.toString(), getName());

            // Take the first shape as the mesh to use for the hair. We
            // assume there is at least one
            AttachmentParams params = new AttachmentParams(
                    getShapes()[0],           // Mesh
                    "HairAttach",             // Parent Joint
                    PMatrix.IDENTITY,         // Orientation
                    "HairAttachmentJoint",    // Attachment Joint Name
                    getModel());              // Owning File Name
            attrs.addAttachmentInstruction(params);
        }

        /**
         * Clones this HairConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new HairConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class HandsConfigElement extends ShapesConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            // add hands
            attrs.addLoadInstruction(getModel());
            attrs.getMetaData().put(ConfigType.HANDS.toString(), getName());

            for (String shape : getShapes()) {
                attrs.addSkinnedMeshParams(new SkinnedMeshParams(shape, "Hands", getModel()));
            }
        }

        /**
         * Clones this HandsConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new HandsConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class TorsoConfigElement extends ShapesConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            attrs.addLoadInstruction(getModel());
            attrs.getMetaData().put(ConfigType.TORSO.toString(), getName());

            for (String shape : getShapes())
                attrs.addSkinnedMeshParams(new SkinnedMeshParams(shape, "UpperBody", getModel()));
        }

        /**
         * Clones this TorsoConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new TorsoConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class JacketConfigElement extends ShapesConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            if (getModel() == null) {
                return;
            }

            attrs.addLoadInstruction(getModel());
            attrs.getMetaData().put(ConfigType.JACKET.toString(), getName());

            for (String shape : getShapes()) {
                attrs.addSkinnedMeshParams(new SkinnedMeshParams(shape, "UpperBody", getModel()));
            }
        }

        /**
         * Clones this JacketConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new JacketConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class LegsConfigElement extends ShapesConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            attrs.addLoadInstruction(getModel());
            attrs.getMetaData().put(ConfigType.LEGS.toString(), getName());

            for (String shape : getShapes()) {
                attrs.addSkinnedMeshParams(new SkinnedMeshParams(shape, "LowerBody", getModel()));
            }
        }

        /**
         * Clones this LegsConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new LegsConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class FeetConfigElement extends ShapesConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            attrs.addLoadInstruction(getModel());
            attrs.getMetaData().put(ConfigType.FEET.toString(), getName());

            for (String shape : getShapes()) {
                attrs.addSkinnedMeshParams(new SkinnedMeshParams(shape, "Feet", getModel()));
            }
        }

        /**
         * Clones this FeetConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new FeetConfigElement();
            }
            return super.clone(element);
        }
    }

    /**
     * Represents a configuration attributes that is an RGB color. Each color
     * component is a floating point between 0.0 and 1.0, inclusive.
     */
    public static abstract class ColorConfigElement extends ConfigElement {
        private float r;
        private float g;
        private float b;

        @XmlTransient
        public void setRGB(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
        
        @XmlElement
        public float getR() {
            return r;
        }

        public void setR(float r) {
            this.r = r;
        }

        @XmlElement
        public float getG() {
            return g;
        }

        public void setG(float g) {
            this.g = g;
        }

        @XmlElement
        public float getB() {
            return b;
        }

        public void setB(float b) {
            this.b = b;
        }

        /**
         * Clones this ColorConfigElement object, making a deep copy. Takes an
         * instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            element = super.clone(element);
            ((ColorConfigElement)element).r = r;
            ((ColorConfigElement)element).g = g;
            ((ColorConfigElement)element).b = b;
            return element;
        }
    }

    public static class SkinColorConfigElement extends ColorConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            // XXX Settings the attributes does nothing XXX
//            attrs.setSkinTone(getR(), getG(), getB());

            String rgb = "" + (int) (getR() * 255) + " " + (int) (getG() * 255) +
                    " " + (int) (getB() * 255);
            attrs.getMetaData().put(ConfigType.SKIN_COLOR.toString(), rgb);
        }

        /**
         * Clones this SkinColorConfigElement object, making a deep copy. Takes
         * an instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new SkinColorConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class HairColorConfigElement extends ColorConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            // XXX Settings the attributes does nothing XXX
//            attrs.setHairColor(getR(), getG(), getB());
            String rgb = "" + (int)(getR() * 255) + " " +(int)(getG() * 255) +
                    " " + (int)(getB() * 255);
            attrs.getMetaData().put(ConfigType.HAIR_COLOR.toString(), rgb);
        }

        /**
         * Clones this HairColorConfigElement object, making a deep copy. Takes
         * an instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new HairColorConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class ShirtColorConfigElement extends ColorConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            // XXX Settings the attributes does nothing XXX
//            attrs.setShirtColor(getR(), getG(), getB(), 0f, 0f, 0f);

            String rgb = "" + (int) (getR() * 255) + " " + (int) (getG() * 255) +
                    " " + (int) (getB() * 255);
            attrs.getMetaData().put(ConfigType.SHIRT_COLOR.toString(), rgb);
        }

        /**
         * Clones this ShirtColorConfigElement object, making a deep copy. Takes
         * an instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new ShirtColorConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class PantsColorConfigElement extends ColorConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            // XXX Settings the attributes does nothing XXX
//            attrs.setPantsColor(getR(), getG(), getB(), 0f, 0f, 0f);

            String rgb = "" + (int) (getR() * 255) + " " + (int) (getG() * 255) +
                    " " + (int) (getB() * 255);
            attrs.getMetaData().put(ConfigType.PANTS_COLOR.toString(), rgb);
        }

        /**
         * Clones this PantsColorConfigElement object, making a deep copy. Takes
         * an instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new PantsColorConfigElement();
            }
            return super.clone(element);
        }
    }

    public static class ShoeColorConfigElement extends ColorConfigElement {
        @Override
        public void apply(CharacterParams attrs) {
            // XXX Settings the attributes does nothing XXX
//            attrs.setShirtColor(getR(), getG(), getB(), 0f, 0f, 0f);

            String rgb = "" + (int) (getR() * 255) + " " + (int) (getG() * 255) +
                    " " + (int) (getB() * 255);
            attrs.getMetaData().put(ConfigType.SHOE_COLOR.toString(), rgb);
        }

        /**
         * Clones this ShoeColorConfigElement object, making a deep copy. Takes
         * an instance of the concrete class to be cloned, returns the cloned
         * objects.
         */
        @Override
        public ConfigElement clone(ConfigElement element) {
            if (element == null) {
                element = new ShoeColorConfigElement();
            }
            return super.clone(element);
        }
    }
    
    @XmlRootElement(name="config-list")
    public static class ConfigList {
        private GenderConfigElement[] genders = new GenderConfigElement[0];
        private HeadConfigElement[]   heads   = new HeadConfigElement[0];
        private HairConfigElement[]   hair    = new HairConfigElement[0];
        private TorsoConfigElement[]  torsos  = new TorsoConfigElement[0];
        private JacketConfigElement[] jackets = new JacketConfigElement[0];
        private HandsConfigElement[]  hands   = new HandsConfigElement[0];
        private LegsConfigElement[]   legs    = new LegsConfigElement[0];
        private FeetConfigElement[]   feet    = new FeetConfigElement[0];

        public GenderConfigElement[] getGenders() {
            return genders;
        }
        
        public void setGenders(GenderConfigElement[] genders) {
            this.genders = genders;
        }

        public HeadConfigElement[] getHeads() {
            return heads;
        }

        public void setHeads(HeadConfigElement[] heads) {
            this.heads = heads;
        }

        public HairConfigElement[] getHair() {
            return hair;
        }

        public void setHair(HairConfigElement[] hair) {
            this.hair = hair;
        }

        public TorsoConfigElement[] getTorsos() {
            return torsos;
        }

        public void setTorsos(TorsoConfigElement[] torsos) {
            this.torsos = torsos;
        }

        public JacketConfigElement[] getJackets() {
            return jackets;
        }

        public void setJackets(JacketConfigElement[] jackets) {
            this.jackets = jackets;
        }

        public HandsConfigElement[] getHands() {
            return hands;
        }

        public void setHands(HandsConfigElement[] hands) {
            this.hands = hands;
        }

        public FeetConfigElement[] getFeet() {
            return feet;
        }

        public void setFeet(FeetConfigElement[] feet) {
            this.feet = feet;
        }

        public LegsConfigElement[] getLegs() {
            return legs;
        }

        public void setLegs(LegsConfigElement[] legs) {
            this.legs = legs;
        }

        public static ConfigList decode(InputStream is) throws IOException {
            try {
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return (ConfigList) unmarshaller.unmarshal(is);
            } catch (JAXBException ex) {
                IOException ioe = new IOException("Unmarshalling error");
                ioe.initCause(ex);
                throw ioe;
            }
        }
    }
}
