package frames;

import clases.Persona;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Logger;
import javax.swing.*;

public class AddContact extends JFrame {

    /**
     * Variable sada para identificar si la ventana empezará con el título
     * de <b>Crear contacto</b> o <b>Editar contacto</b>
     */
    boolean contactoNuevo = true;

    /**
     * Se crea un objeto de <b>MainMenu</b>
     */
    MainMenu mainMenu;

    /**
     * Se crea un objeto de la clase AddContact
     */
    static AddContact ac;

    /**
     * Se crea un objeto de la clase Persona la cual será el contacto seleccionado
     */
    Persona contactSelected;

    /**
     * Se crea instancia una lista de contactos
     */
    LinkedList<Persona> contactos = new LinkedList<>();

    /**
     * Se crea un objeto de la clase File para guardar el archivo
     */
    File file;

    /**
     * Constructor normal vacío para inicializar todos los componentes y las propiedades de esta ventana
     */
    public AddContact(){
        initAll();
    }

    /**
     * Constructor para inicializar todos los componentes y las propiedades de esta ventana, la lista de contactos y el archivo
     */
    public AddContact(LinkedList<Persona> contactos, File file) {
        this.contactos = contactos;
        this.file = file;
        initAll();
    }

    /**
     * Constructor para inicializar todos los componentes y las propiedades de esta ventana y además
     * cambiar las propiedades de la ventana, como el título, ya que este constructor se usa para
     * abrir la ventana en modo de edición de contacto
     */
    public AddContact(LinkedList<Persona> contactos, File file, Persona contactSelected) {
        this.contactSelected = contactSelected;
        initAll();
        txtName.setText(contactSelected.getNombre());
        txtAddress.setText(contactSelected.getDireccion());
        txtPhone.setText(contactSelected.getTelefono());
        txtAge.setText(contactSelected.getEdad());
        this.file = file;
        this.contactos = contactos;
        contactoNuevo = false;
        setTitle("Editar contacto");
        jLabel1.setText("Editar contacto");
    }

    /**
     * Método para inicializar los componentes y las propiedades de la ventana
     */
    public final void initAll(){
        initComponents();
        initProperties();
    }

    /**
     * Método para inicializar las propiedades de la ventana así como para agregar algunos KeyListeners
     * básicos de cada campo
     */
    public void initProperties(){
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Agregar contacto");
        setResizable(false);
        setSize(700, 500);
        setKeyListeners(txtAge);
        setKeyListeners(txtName);
        setKeyListeners(txtAddress);
        setKeyListeners(txtPhone);
    }

    /**
     * Método para agregar KeyListeners a los campos de texto y así sea posible usar las teclas
     * <b>ENTER</b> y <b>ESCAPE</b>
     * @param txt
     */
    private void setKeyListeners(JTextField txt) {
        txt.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Si la tecla presionada es enter, guardará los cambios o agregará a la persona, depende de
                    // cómo se esté usando esta ventana
                    save();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Si la tecla presionada es escape, se ejecutará el método para cancelar el registro o actualización
                    // de la información del contacto
                    cancel();
                }
            }
        });
    }

    /**
     * Método para inicializar los componentes de la ventana
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        txtName = new JTextField();
        jLabel3 = new JLabel();
        txtPhone = new JTextField();
        jLabel4 = new JLabel();
        txtAddress = new JTextField();
        jLabel5 = new JLabel();
        txtAge = new JTextField();
        btCancel = new JButton();
        btSaveContact = new JButton();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Al presionar la X se cerrará la ventana

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Se setea el estilo y la fuente del JLabel
        jLabel1.setText("Agregar contacto"); // Se setea el texto que contendrá el JLabel

        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N
        jLabel2.setText("Nombre:");

        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // se setea el estilo y la fuente del JTextField

        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N
        jLabel3.setText("Teléfono:");

        txtPhone.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N

        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N
        jLabel4.setText("Dirección:");

        txtAddress.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N

        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N
        jLabel5.setText("Edad:");

        txtAge.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N

        btCancel.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Se setea el estilo y la fuente del texto del botón
        btCancel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/cerrar.png")))); // Se agrega el ícono
        btCancel.setText("Cancelar"); // Se setea el texto del botón
        btCancel.addActionListener(this::btCancelActionPerformed); // Se le agrega un Evento

        btSaveContact.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N
        btSaveContact.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/disquete.png")))); // NOI18N
        btSaveContact.setText("Guardar");
        btSaveContact.addActionListener(this::btSaveContactActionPerformed);

        //Código generado por netbeans
        {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(202, 202, 202))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel3)
                                                                        .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, 460, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtAge, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel5))
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(txtAddress, GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(txtName))
                                                .addGap(48, 48, 48))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(159, 159, 159)
                                .addComponent(btCancel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btSaveContact)
                                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btCancel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btSaveContact, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }
    }

    /**
     * Método para asignarle una acción al darle clic al botón de <b>Guardar</b>
     * @param evt
     */
    private void btSaveContactActionPerformed(ActionEvent evt) {
        save();
    }

    /**
     * Método para asignarle una acción al darle clic al botón de <b>Cancelar</b>
     * @param evt
     */
    private void btCancelActionPerformed(ActionEvent evt) {
        cancel();
    }

    /**
     * Método para cancelar
     */
    public void cancel(){
        dispose();
        mainMenu = new MainMenu(file, contactos);
        mainMenu.setVisible(true);
    }

    /**
     * Método para guardar un contacto o para guardar los cambios hechos a la información de un contacto
     */
    public void save(){
        String name, address, phone, age; // Se declaran los atributos del objeto Persona
        if (contactoNuevo) { // Se valida si se va a guardar un contacto nuevo o un contacto ya existente
            if (!txtName.getText().trim().equals("") &&
                    !txtAddress.getText().trim().equals("") &&
                    !txtPhone.getText().trim().equals("") &&
                    !txtAge.getText().trim().equals("")) { // en caso de que sea un contacto nuevo se va a validar que no haya campos vacíos
                name = txtName.getText().trim(); // Se asigna el nombre
                address = txtAddress.getText().trim(); // Se asigna la dirección
                try {
                    Long.parseLong(txtPhone.getText().trim()); // Se verifica que el número de teléfono conste de sólo números
                    Long.parseLong(txtAge.getText().trim()); // Se verifique que la edad sea un numero
                    age = txtAge.getText().trim(); // En caso de que no haya causado una excepción, se va asignar la edad
                    phone = txtPhone.getText().trim(); // Y se va a asignar el teléfono
                    if (contactos.isEmpty()){ // Se valida si la lista está vacía o no
                        contactos.add(new Persona(1 + "", name, address, phone, age)); // Se agregará el nuevo contacto a la lista
                        // En caso de que esté vacía, el contacto siempre tendrá el ID 1
                    } else {
                        // En caso de que no, agarrará el ID del último contacto agregado y le sumará 1
                        contactos.add(new Persona((Integer.parseInt(contactos.peekLast().getId())+1) + "", name, address, phone, age));
                    }
                    overwriteFile(); // Se sobreescribirá el archivo
                    mainMenu = new MainMenu(file, contactos); // Se instanciará el objeto de la clase MainMenu
                    mainMenu.setVisible(true); // Se mostrará la nueva ventana
                    dispose(); // Se cerrará esta ventana
                } catch (NumberFormatException e) { // En caso de que el número de teléfono y/o la edad sea inválidos, se le notificará al usuario
                    JOptionPane.showMessageDialog(null, "El número de teléfono y/o edad ingresados son inválidos");
                }
            } else {
                // En caso de que haya algún campo vacío, se le notificará al usuario
                JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos");
            }
        } else {
            // Si el contacto no es nuevo, no habrá mucha diferencia en códifo
            if (!txtName.getText().trim().equals("") &&
                    !txtAddress.getText().trim().equals("") &&
                    !txtPhone.getText().trim().equals("") &&
                    !txtAge.getText().trim().equals("")) {
                name = txtName.getText().trim();
                address = txtAddress.getText().trim();
                try {
                    Long.parseLong(txtPhone.getText().trim());
                    Long.parseLong(txtAge.getText().trim());
                    age = txtAge.getText().trim();
                    phone = txtPhone.getText().trim();
                    // Se utilizan los métodos set para cambiar los campos del contacto
                    contactSelected.setNombre(name);
                    contactSelected.setDireccion(address);
                    contactSelected.setTelefono(phone);
                    contactSelected.setEdad(age);
                    // Y es toda la diferencia que hay entre agregar un nuevo contacto y editar uno ya existente
                    overwriteFile();
                    mainMenu = new MainMenu(file, contactos);
                    mainMenu.setVisible(true);
                    dispose();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "El número de teléfono y/o edad ingresados son inválidos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos");
            }
        }
    }

    public void overwriteFile() {
        FileWriter fw = null;
        PrintWriter pw = null;
        // Se declaran los objetos para escribir en el archivo
        try{
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);
            // Se inicializan ambos objetos
            String line; // Se declara una variable llamada line de tipo String, es donde tendrá cada iteración
            for (Persona persona: contactos){
                line = persona.getId()+","+persona.getNombre()+","+persona.getDireccion()+","+persona.getTelefono()+","+persona.getEdad();
                // Cada iteración del for each va a guardar en la variable todos los datos separados por comas
                pw.println(line);
                // Al final simplemente se imprimen las iteraciones en el archivo
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al sobreescribir el archivo");
        } finally {
            // Finalmente se cierran los streams
            try {
                if (fw != null) {
                    fw.close();
                }
                if (pw != null){
                    pw.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Método main
     * @param args
     */
    public static void main(String[] args) {
        FlatLightLaf.setup();

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AddContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        ac = new AddContact();
        ac.setVisible(true);
    }

    // Variables declaration - do not modify                     
    JButton btCancel;
    JButton btSaveContact;
    JLabel jLabel1;
    JLabel jLabel2;
    JLabel jLabel3;
    JLabel jLabel4;
    JLabel jLabel5;
    JTextField txtAddress;
    JTextField txtAge;
    JTextField txtName;
    JTextField txtPhone;
    // End of variables declaration                   
}