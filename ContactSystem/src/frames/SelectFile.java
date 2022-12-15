package frames;

import clases.Persona;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class SelectFile extends JFrame {
    /**
     * Controla la instancia de la primera vez que se corre esta ventana
     */
    static SelectFile sf;

    /**
     * Se encarga de almacenar el archivo .csv que contiene los contactos
     */
    File file;

    /**
     * Guarda como String la ruta del archivo seleccionado, o en su defecto guarda la
     * ruta del archivo ingresada por texto por el usuario
     */
    public String ruta;

    /**
     * Objeto para instanciar la ventana del menú principal, mismo el cual la abre
     */
    MainMenu mainMenu;

    /**
     * Constructor vacío para inicializar todos los componentes, objetos y variables
     */
    public SelectFile() {
        initAll();
    }

    /**
     * Constructor para inicializar todo,
     * permite la inicialización específica del archivo .csv y el menú
     * @param file
     * @param mainMenu
     */
    public SelectFile(File file, MainMenu mainMenu){
        initAll();
        this.file = file;
        tfRuta.setText(file.getAbsolutePath());
        this.mainMenu = mainMenu;
    }

    /**
     * Método que inicializa los componentes y las propiedades de esta ventana
     */
    public final void initAll(){
        initComponents();
        initProperties();
    }

    /**
     * Método para inicializar las propiedades de la ventana
     */
    public void initProperties(){
        setLayout(null); // Sin layout
        setLocationRelativeTo(null); // La ventana se posiciona en el centro de la pantalla por defecto
        setTitle("Seleccionar Archivo"); // Se fija el título de la ventana
        setResizable(false); // Se restringe la opción de cambiar el tamaño de la ventana
    }

    /**
     * Método que inicializa los componentes y les asigna sus propiedades
     */
    private void initComponents() {
        // Inicialización de los componentes necesarios
        jLabel1 = new JLabel();
        tfRuta = new JTextField();
        btOpen = new JButton();
        btCancel = new JButton();
        btAccept = new JButton();
        jLabel2 = new JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Cierra la ventana al darle clic a la X

        // Asignación de las propiedades del JLabel del título de la ventana
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Estilo del texto
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER); // Alineación del texto
        jLabel1.setText("Seleccionar Archivo"); // Asignación del texto

        // Asignación de las propiedades
        tfRuta.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        tfRuta.addKeyListener(new KeyAdapter(){
            @Override
            /*
             * Se le agrega un KeyListener al JTextField para que, en caso de que
             * presione la tecla "ENTER" haga la misma acción que darle clic al
             * botón de "Aceptar", y en caso de que se presione la tecla "ESCAPE"
             * se cierre la ventana
             */
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    accept(); // Acción del botón "Aceptar"
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose(); // Cierra la ventana, en caso de que haya más ventanas abiertas no termina el proceso
                               // de lo contrario, cierra la única ventana abierta y termina el proceso
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O){ // Si el usuario presiona la combinación de teclas ctrl + o
                    // Abrirá la ventana del explorador de archivos para seleccionar un archivo
                    open();
                }
            }
        });

        btOpen.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btOpen.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/abrir-esquema-de-carpeta.png")))); // Agrega el ícono al botón
        btOpen.setText("Abrir...");
        btOpen.addActionListener(this::btOpenActionPerformed); // A través de un addActionListener se le indica qué método se ejecutará cuando dé clic al botón

        btCancel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btCancel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/cerrar.png"))));
        btCancel.setText("Cancelar");
        btCancel.addActionListener(this::btCancelActionPerformed);

        btAccept.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btAccept.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/garrapata.png"))));
        btAccept.setText("Aceptar");
        btAccept.addActionListener(this::btAcceptActionPerformed);

        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        jLabel2.setText("Ruta:");

        //Código generado por netbeans, básicamente le da forma (tamaño) a los componentes y a la ventana
        {
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(61, 61, 61)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(tfRuta, GroupLayout.PREFERRED_SIZE, 701, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(btOpen, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap(66, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
                                    .addGap(303, 303, 303))
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(291, 291, 291)
                                    .addComponent(btCancel)
                                    .addGap(124, 124, 124)
                                    .addComponent(btAccept)
                                    .addGap(0, 0, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(tfRuta, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btOpen, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                                    .addGap(27, 27, 27)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(btCancel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btAccept, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(30, Short.MAX_VALUE))
            );

            pack();
        }
    }

    /**
     * Método que le dice al botón de "<b>Abrir...</b>" que abra un JFileChooser y el
     * usuario pueda elegir el archivo <u><b>únicamente</b></u> con extensión .csv
     * @param evt
     */
    private void btOpenActionPerformed(ActionEvent evt) {
        open();
    }

    public void open(){
        JFileChooser fileChooser = new JFileChooser(); // Se declara e inicializa el JFileChooser
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo csv", "csv"); // Se declara e inicializa el filtro de extensiones de archivo
        fileChooser.setFileFilter(filtro); // Se le fija el filtro creado al JFileChooser

        fileChooser.showOpenDialog(this); // Se abre la ventana JFileChooser para elegir el archivo
        tfRuta.requestFocus(); // Una vez cerrada la ventana del JFileChooser se solicita un focus al JTextField de la ruta
        file = fileChooser.getSelectedFile(); // Se obtiene el archivo seleccionado y se le asigna al objeto "file"
        if (file != null) {
            tfRuta.setText(file.getAbsolutePath());
            // En caso de que el archivo seleccionado no sea nulo se va a obtener la ruta y se escribirá en el JTextField de la misma
        }
    }

    /**
     * Método que le dice al botón "<b>Aceptar</b>" qué hacer cuando se le dé clic
     * @param evt
     */
    private void btAcceptActionPerformed(ActionEvent evt) {
        accept();
    }

    /**
     * Método que sirve para dar clic al botón "<b>Aceptar</b>" o para presionar la tecla <b>Enter</b>
     */
    public void accept(){
        ruta = tfRuta.getText().trim(); // Se obtiene la ruta (en caso de que el usuario haya escrito la ruta)
        if (!ruta.equals("")) {
            if (validate(ruta)) { // Si la ruta no está vacía, se valida si es una ruta correcta
                file = new File(ruta); // Se obtendrá el archivo desde la ruta del JTextField
                this.dispose(); // Se cerrará esta ventana
                if (mainMenu != null) {
                    mainMenu.dispose(); // En caso de que la ventana del Menú Principal esté abierta, la va a cerrar
                }
                MainMenu mainMenu = new MainMenu(file); // Se instancia un objeto de la clase MainMenú, pero con el archivo cargado
                mainMenu.setVisible(true); // Se hace visible la ventana
            }
        } else {
            // Si la ruta está vacía, se lanzará un mensaje de alerta
            JOptionPane.showMessageDialog(null, "Selecciona un archivo o escribe una ruta");
        }
    }

    /**
     * Método para validar que la ruta tenga el formato correcto
     * @param ruta
     */
    public boolean validate(String ruta) {
        if (!ruta.toLowerCase().endsWith(".csv")) { // En caso de que la ruta no termine con .csv se mostrará un mensaje de alerta y retornará false
            JOptionPane.showMessageDialog(null, "Formato de archivo incorrecto");
            return false;
        }
        if (!ruta.toLowerCase().contains("\\")) { // En caso de que la ruta no contenga \ en algún lado se mostrará un mensaje de alerta y retornará falso
            JOptionPane.showMessageDialog(null, "Ruta inválida");
            return false;
        }
        // Si ninguna de las opciones anteriores no se cumple, entonces retornará true
        return validateFile();
    }

    /**
     * Método para validar si el archivo es válido o no, es decir,
     * corrobora que sí cumpla con el número de columnas correcto
     */
    public boolean validateFile(){
        FileReader fr = null; // Se declara un FileReader
        BufferedReader br = null; // Se declara un BufferedReader
        try{
            fr = new FileReader(file); // Se inicializa el FileReader con el archivo a leer
            br = new BufferedReader(fr); // Se inicializa el BufferedReader con el FileReader
            String line; // Se declara una variable String para guardar línea por línea del archivo

            while((line = br.readLine()) != null){ // Se actualiza la variable line al mismo tiempo que se compara si es nula
                String[] datosPersona = line.split(",");// En un arreglo de String se va a almacenar cada elemento separado por comas de la línea
                Persona persona = new Persona(); // Se instancía un objeto de la clase Persona

                if (datosPersona.length == 5){  // Se verifica que la longitud del arreglo sea igual al número de columnas en la tabla
                    // Se llenan los datos en la tabla
                    persona.setId(datosPersona[0]);
                    persona.setNombre(datosPersona[1]);
                    persona.setDireccion(datosPersona[2]);
                    persona.setTelefono(datosPersona[3]);
                    persona.setEdad(datosPersona[4]);
                } else {
                    persona.setId(datosPersona[-1]); // En caso de que la longitud del arreglo no coincida, se provoca una excepción
                }
            }
            return true;
        }catch(IOException e){
            // En caso de que no se haya podido leer el archivo se desplegará un mensaje de alerta
            JOptionPane.showMessageDialog(null, "Error al leer el archivo");
        } catch (ArrayIndexOutOfBoundsException ex){
            // En caso de que la longitud del arreglo sea incorrecta, se desplegará un mensaje de alerta
            JOptionPane.showMessageDialog(null, "Error, archivo no compatible");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null){
                    fr.close();
                }
                // Se cierran conexiones de los objetos usados
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Método que le indica al boton de "<b>Cancelar</b>" qué hacer en caso de que sea presionado
     * @param evt
     */
    private void btCancelActionPerformed(ActionEvent evt) {
        dispose();
    }

    /**
     * Método main
     * @param args
     */
    public static void main(String[] args) {
        FlatLightLaf.setup(); // Se configura el estilo de la ventana

        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Se fija el LookAndFeel (estilo) de la ventana al recientemente configurado
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SelectFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        sf = new SelectFile(); // Se inicializa la instancia de la clase SelectFile
        sf.setVisible(true); // Se hace visible la ventana
    }

    // Declaración de variables
    JButton btAccept;
    JButton btCancel;
    JButton btOpen;
    JLabel jLabel1;
    JLabel jLabel2;
    JTextField tfRuta;
    // Fin de la declaración de variables
}
