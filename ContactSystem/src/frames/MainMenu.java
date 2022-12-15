package frames;

import clases.Persona;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class MainMenu extends JFrame {

    /**
     * Instancia de la clase MainMenu
     */
    static MainMenu mainMenu;

    /**
     * Objeto de la clase DefaultTableModel para poder cambiar la
     * estructura de la tabla y obtener valores de la misma
     */
    DefaultTableModel model;

    /**
     * Objeto de la clase Persona, usado para guardar el contacto seleccionado de la tabla
     */
    Persona contactSelected;

    /**
     * Objeto de la clase File para guardar el archivo con el que se está trabajando
     */
    File file;

    /**
     * Lista para manejar los contactos sacados del archivo y los nuevos agregados
     */
    LinkedList<Persona> contactos = new LinkedList<>();

    //Pilas para manejar el ordenamiento más optimizadamente
    Stack<Persona> descIdOrdered = new Stack<>();
    Stack<Persona> descNameOrdered = new Stack<>();
    Stack<Persona> descAddressOrdered = new Stack<>();
    Stack<Persona> descPhoneOrdered = new Stack<>();
    Stack<Persona> descAgeOrdered = new Stack<>();
    Stack<Persona> ascIdOrdered  = new Stack<>();
    Stack<Persona> ascNameOrdered = new Stack<>();
    Stack<Persona> ascAddressOrdered = new Stack<>();
    Stack<Persona> ascPhoneOrdered = new Stack<>();
    Stack<Persona> ascAgeOrdered = new Stack<>();

    /**
     * Constructor para inicializar los componentes, las propiedades de la ventana y el archivo del
     * cual se tomarán los contactos y se escribirán los mismos
     */
    public MainMenu(File file){
        this.file = file;
        initAll();
    }

    /**
     * Constructor vacío para inicializar los componentes y las propiedades de la ventana
     */
    public MainMenu() {
        initAll();
    }

    /**
     * Constructor para inicializar los componentes, las propiedades de la ventana, el archivo del
     * cual se tomarán los contactos y se escribirán los mismos, y la lista de la cual se tomarán
     * todos los contactos
     * @param file
     * @param contactos
     */
    public MainMenu(File file, LinkedList<Persona> contactos) {
        this.file = file;
        this.contactos = contactos;
        initAll();
    }

    /**
     * Método para inicializar los componentes y las propiedades de la ventana
     */
    public final void initAll(){
        initComponents();
        initProperties();
        initStructures();
    }

    /**
     * Método para inicializar las pilas del programa
     */
    void initStructures() {
        ascIdOrdered = listToStack(sortById(true));
        ascNameOrdered = listToStack(sortByName(true));
        ascAddressOrdered = listToStack(sortByAddress(true));
        ascPhoneOrdered = listToStack(sortByPhone(true));
        ascAgeOrdered = listToStack(sortByAge(true));

        descIdOrdered = invertStack(listToStack(sortById(true)));
        descNameOrdered = invertStack(listToStack(sortByName(true)));
        descAddressOrdered = invertStack(listToStack(sortByAddress(true)));
        descPhoneOrdered = invertStack(listToStack(sortByPhone(true)));
        descAgeOrdered = invertStack(listToStack(sortByAge(true)));

        sortById(true);
    }

    /**
     * Método que inicializa los componentes y las propiedades de esta ventana
     */
    public void initProperties(){
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Contactos");
        setResizable(false);
        setSize(1000, 800);
        if (readFile()){
            updateTable();
        }
        btAddContact.requestFocus();
    }

    /**
     * Método para inicializar las propiedades de la ventana
     */
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        tableContactos = new JTable(){
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }};
        btAddContact = new JButton();
        btDeleteContact = new JButton();
        jLabel3 = new JLabel();
        jMenuBar1 = new JMenuBar();
        menuFile = new JMenu();
        itemOpen = new JMenuItem();
        itemClose = new JMenuItem();
        menuEdit = new JMenu();
        orderMenu = new JMenu();
        ascMenu = new JMenu();
        ascId = new JMenuItem();
        ascName = new JMenuItem();
        ascAddress = new JMenuItem();
        ascPhone = new JMenuItem();
        ascAge = new JMenuItem();
        descMenu = new JMenu();
        descId = new JMenuItem();
        descName = new JMenuItem();
        descAddress = new JMenuItem();
        descPhone = new JMenuItem();
        descAge = new JMenuItem();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Cierra la ventana al darle clic a la X

        // Seteo de las propiedades de la tabla
        tableContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Hace que solo se pueda seleccionar una fila a la vez
        tableContactos.getTableHeader().setReorderingAllowed(false); // Restringe el movimiento de cada columna y no permite que se reordene
        // Agrega eventos de mouse a la tabla, esto hace posible que pueda detectar clics y programar las acciones al dar clic
        tableContactos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                contactSelected = contactos.get(tableContactos.getSelectedRow()); // Cada que haga clic se va a obtener el contacto seleccionado
                if (e.getClickCount()==2) {
                    enter(); // En caso de que haga doble clic se va a ejecutar el mismo método que si se pulsara la tecla ENTER
                }
            }
        });
        // Se le agregan eventos de teclado a la tabla
        tableContactos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    if (contactSelected != null){
                        delete(); // Si la tecla presionada es SUPRIMIR o BACKSPACE y hay un contacto seleccionado, va a ejecutar el método para borrar el contacto
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    dispose(); // Si la tecla presionada es ESCAPE se cerrará esta ventana
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    enter(); // Si la tecla presionada es ENTER se va a abrir la ventana para editar datos del usuario
                }
            }
        });
        jScrollPane1.setViewportView(tableContactos); // Se le agrega un Scroll Pane a la tabla por si hay muchos registros

        btAddContact.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Se setea la fuente y estilo del botón
        btAddContact.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/add-user.png")))); // Se le pone un ícono al botón
        btAddContact.setText("Agregar contacto"); // Se le agrega el texto del botón
        btAddContact.setHideActionText(true);
        btAddContact.addActionListener(this::btAddContactActionPerformed); // Se le agrega el Evento de clic y se le referencía qué hará al momento de clickear el botón
        btAddContact.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){ //Se le agrega el evento de teclado al botón
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    //Si la tecla que se presiona es ESCAPE, entonces se cerrará esta ventana
                    dispose();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){ // Si la tecla presionada es la tecla de la flecha hacia arriba o hacia abajo...
                    tableContactos.requestFocus();
                    if (tableContactos.getSelectedRow() > 0) {
                        contactSelected = contactos.get(tableContactos.getSelectedRow());
                    }
                    // ... hará un focus a la tabla para que también se pueda mover entre elementos seleccionados en la tabla con las teclas de flechas
                    // y simplemente se va a actualizar el contacto seleccionado
                }
            }
        });

        btDeleteContact.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // NOI18N
        btDeleteContact.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/basura.png")))); // NOI18N
        btDeleteContact.setText("Eliminar contacto");
        btDeleteContact.setHideActionText(true);
        btDeleteContact.addActionListener(this::btDeleteContactActionPerformed);

        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Se setea la fuente y el estilo del label de texto
        jLabel3.setText("Contactos"); // Se setea el texto que tendrá el label

        menuFile.setText("File"); // Se agrega un menú

        itemOpen.setText("Abrir"); // Se setea el texto del ítem
        itemOpen.addActionListener(this::itemOpenActionPerformed); // Se le agrega un Evento de clic al ítem agregado
        menuFile.add(itemOpen); // Se agrega el ítem al menú

        itemClose.setText("Salir"); // Se setea el texto de otro ítem del menú
        itemClose.addActionListener(evt -> itemCloseActionPerformed()); // Se le agrega un Evento de clic al nuevo ítem
        menuFile.add(itemClose); // Se agrega el nuevo ítem al menú

        jMenuBar1.add(menuFile); // Se agrega el menú a la barra de menú

        menuEdit.setText("Edit"); // Se setea el texto de otro menú

        orderMenu.setText("Ordenar"); // Se setea el texto del submenú del menú

        ascMenu.setText("Ascendente"); // Se setea el texto del submenú del submenú

        // Seteo y adición de los ítems a los menús y los Eventos de los mismos
        ascId.setText("ID");
        ascId.addActionListener(this::ascIdActionPerformed);
        ascMenu.add(ascId);

        ascName.setText("Nombre");
        ascName.addActionListener(this::ascNameActionPerformed);
        ascMenu.add(ascName);

        ascAddress.setText("Dirección");
        ascAddress.addActionListener(this::ascAddressActionPerformed);
        ascMenu.add(ascAddress);

        ascPhone.setText("Teléfono");
        ascPhone.addActionListener(this::ascPhoneActionPerformed);
        ascMenu.add(ascPhone);

        ascAge.setText("Edad");
        ascAge.addActionListener(this::ascAgeActionPerformed);
        ascMenu.add(ascAge);

        orderMenu.add(ascMenu); // Se agrega el submenú al submenú

        /* Separador entre los códigos de los menús ascendente y descendente */

        descMenu.setText("Descendente");

        descId.setText("ID");
        descId.addActionListener(this::descIdActionPerformed);
        descMenu.add(descId);

        descName.setText("Nombre");
        descName.addActionListener(this::descNameActionPerformed);
        descMenu.add(descName);

        descAddress.setText("Dirección");
        descAddress.addActionListener(this::descAddressActionPerformed);
        descMenu.add(descAddress);

        descPhone.setText("Teléfono");
        descPhone.addActionListener(this::descPhoneActionPerformed);
        descMenu.add(descPhone);

        descAge.setText("Edad");
        descAge.addActionListener(this::descAgeActionPerformed);
        descMenu.add(descAge);

        // Fin del seteo y adición de los ítems a los menús y los Eventos de los mismos

        orderMenu.add(descMenu); // Se agrega el submenú al submenú

        menuEdit.add(orderMenu); // Se agrega el submenú al menú

        jMenuBar1.add(menuEdit); // Se agrega el menú a la barra de menú

        setJMenuBar(jMenuBar1); // Se agrega la barra de menú a la ventana
        //Código generado por netbeans
        {
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(420, 420, 420)
                                    .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(280, 280, 280)
                                    .addComponent(btAddContact, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                    .addGap(73, 73, 73)
                                    .addComponent(btDeleteContact, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGap(40, 40, 40)
                                    .addComponent(jLabel3)
                                    .addGap(42, 42, 42)
                                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 510, GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(btAddContact, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btDeleteContact, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(32, Short.MAX_VALUE))
            );

            pack();
        }
    }

    /**
     * Método que equivale a darle doble clic o presionar la tecla enter a un contacto seleccionado,
     * abre la ventana para editar los datos de un contacto
     */
    public void enter(){
        contactSelected = contactos.get(tableContactos.getSelectedRow()); // Se obtiene el contacto seleccionado en la tabla
        dispose(); // Se cierra la ventana
        AddContact addContact = new AddContact(contactos, file, contactSelected); // Se instancia un objeto de la clase AddContact, pasando como parametro el archivo, la lista y el contacto seleccionado
        addContact.setVisible(true); // Se abre la ventana
    }

    /**
     * Método que se ejecuta al darle clic al ítem <b>Salir</b> del menú <b>Archivo</b> y este
     * mismo cerrará el programa
     */
    private void itemCloseActionPerformed() {
        System.exit(0);
    }

    /**
     * Método que se ejecuta al darle clic al ítem <b>Abrir</b> del menú <b>Archivo</b> y este mismo
     * abrirá una ventana para abrir un nuevo archivo
     * @param evt
     */
    private void itemOpenActionPerformed(ActionEvent evt) {
        SelectFile sf = new SelectFile(file, this);
        sf.setVisible(true);
    }

    /**
     * Método que abre la ventana para agregar un nuevo contacto
     * @param evt
     */
    private void btAddContactActionPerformed(ActionEvent evt) {
        AddContact addContact = new AddContact(contactos, file);
        addContact.setVisible(true);
        dispose();
    }

    /**
     * Método que sirve para eliminar un contacto seleccionado
     * @param evt
     */
    private void btDeleteContactActionPerformed(ActionEvent evt) {
        delete();
    }

    /**
     * Método que borra un contacto seleccionado desde la tabla, así mismo como actualiza la tabla y sobreescribe el archivo
     */
    public void delete(){
        if (contactSelected != null){
            contactos.remove(contactSelected);
            updateTable();
            overwriteFile();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un contacto primero");
        }
    }

    /**
     * Método para ordenar la tabla por ID ascendentemente
     * @param evt
     */
    private void ascIdActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(ascIdOrdered);
    }

    /**
     * Método para ordenar la tabla por Nombre ascendentemente
     * @param evt
     */
    private void ascNameActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(ascNameOrdered);
    }

    /**
     * Método para ordenar la tabla por Dirección ascendentemente
     * @param evt
     */
    private void ascAddressActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(ascAddressOrdered);
    }

    /**
     * Método para ordenar la tabla por Número de teléfono ascendentemente
     * @param evt
     */
    private void ascPhoneActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(ascPhoneOrdered);
    }

    /**
     * Método para ordenar la tabla por Edad ascendentemente
     * @param evt
     */
    private void ascAgeActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(ascAgeOrdered);
    }

    /**
     * Método para ordenar la tabla por ID descendentemente
     * @param evt
     */
    private void descIdActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(descIdOrdered);
    }

    /**
     * Método para ordenar la tabla por Nombre descendentemente
     * @param evt
     */
    private void descNameActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(descNameOrdered);
    }

    /**
     * Método para ordenar la tabla por Dirección descendentemente
     * @param evt
     */
    private void descAddressActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(descAddressOrdered);
    }

    /**
     * Método para ordenar la tabla por Número de teléfono descendentemente
     * @param evt
     */
    private void descPhoneActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(descPhoneOrdered);
    }

    /**
     * Método para ordenar la tabla por Edad descendentemente
     * @param evt
     */
    private void descAgeActionPerformed(ActionEvent evt) {
        initStructures();
        updateTable(descAgeOrdered);
    }

    /**
     * Método para ordenar por ID por método burbuja, requiere un parámetro para ver si el
     * ordenamiento se hace ascendentemente o descendentemente
     * @param asc
     */
    public LinkedList<Persona> sortById(boolean asc) {
        Persona aux;
        for(int i = 0;i < contactos.size()-1;i++){
            for(int j = 0;j < contactos.size()-i-1;j++){
                if (asc){
                    if (contactos.get(j + 1).getId().compareToIgnoreCase(contactos.get(j).getId()) < 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                } else {
                    if (contactos.get(j + 1).getId().compareToIgnoreCase(contactos.get(j).getId()) > 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                }
            }
        }
        updateTable();
        return contactos;
    }

    /**
     * Método para ordenar por Nombre por método burbuja, requiere un parámetro para ver si el
     * ordenamiento se hace ascendentemente o descendentemente
     * @param asc
     */
    public LinkedList<Persona> sortByName(boolean asc) {
        Persona aux;
        for(int i = 0;i < contactos.size()-1;i++){
            for(int j = 0;j < contactos.size()-i-1;j++){
                if (asc){
                    if (contactos.get(j + 1).getNombre().compareToIgnoreCase(contactos.get(j).getNombre()) < 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                } else {
                    if (contactos.get(j + 1).getNombre().compareToIgnoreCase(contactos.get(j).getNombre()) > 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                }
            }
        }
        updateTable();
        return contactos;
    }

    /**
     * Método para ordenar por Dirección por método burbuja, requiere un parámetro para ver si el
     * ordenamiento se hace ascendentemente o descendentemente
     * @param asc
     */
    public LinkedList<Persona> sortByAddress(boolean asc) {
        Persona aux;
        for(int i = 0;i < contactos.size()-1;i++){
            for(int j = 0;j < contactos.size()-i-1;j++){
                if (asc){
                    if (contactos.get(j + 1).getDireccion().compareToIgnoreCase(contactos.get(j).getDireccion()) < 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                } else {
                    if (contactos.get(j + 1).getDireccion().compareToIgnoreCase(contactos.get(j).getDireccion()) > 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                }
            }
        }
        updateTable();
        return contactos;
    }

    /**
     * Método para ordenar por Número de teléfono por método burbuja, requiere un parámetro para ver si el
     * ordenamiento se hace ascendentemente o descendentemente
     * @param asc
     */
    public LinkedList<Persona> sortByPhone(boolean asc) {
        Persona aux;
        for(int i = 0;i < contactos.size()-1;i++){
            for(int j = 0;j < contactos.size()-i-1;j++){
                if (asc){
                    if (contactos.get(j + 1).getTelefono().compareToIgnoreCase(contactos.get(j).getTelefono()) < 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                } else {
                    if (contactos.get(j + 1).getTelefono().compareToIgnoreCase(contactos.get(j).getTelefono()) > 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                }
            }
        }
        updateTable();
        return contactos;
    }

    /**
     * Método para ordenar por Edad por método burbuja, requiere un parámetro para ver si el
     * ordenamiento se hace ascendentemente o descendentemente
     * @param asc
     */
    public LinkedList<Persona> sortByAge(boolean asc) {
        Persona aux;
        for(int i = 0;i < contactos.size()-1;i++){
            for(int j = 0;j < contactos.size()-i-1;j++){
                if (asc){
                    if (contactos.get(j + 1).getEdad().compareToIgnoreCase(contactos.get(j).getEdad()) < 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                } else {
                    if (contactos.get(j + 1).getEdad().compareToIgnoreCase(contactos.get(j).getEdad()) > 0) {
                        aux = contactos.get(j + 1);
                        contactos.set(j + 1, contactos.get(j));
                        contactos.set(j, aux);
                    }
                }
            }
        }
        updateTable();
        return contactos;
    }

    /**
     * Método para leer el archivo .csv
     * @return
     */
    public boolean readFile(){
        FileReader fr = null; // Se declara un FileReader
        BufferedReader br = null; // Se declara un BufferedReader
        try{
            contactos = new LinkedList<>(); // Se inicializa la lista de contactos
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
                    // Se agrega a la persona a la lista
                    contactos.add(persona);
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
     * Método para sobreescribir el archivo
     */
    public void overwriteFile() {
        sortById(true); // La cola se ordena ascendentemente por id
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
     * Método para invertir una pila, esto lo hace sin dejar vacía alguna pila
     * @param personas
     * @return
     */
    public Stack<Persona> invertStack(Stack<Persona> personas){
        Stack<Persona> newStack = new Stack<>();
        while(personas.size() > 0){
            Persona persona = personas.pop();
            newStack.push(persona);
        }
        return newStack;
    }

    /**
     * Método para asignar el contenido de una lista en una pila
     * en el mismo orden en el que vengan los datos, es decir, no
     * invierte los datos de la pila
     * @param personas
     * @return
     */
    public Stack<Persona> listToStack(LinkedList<Persona> personas){
        Stack<Persona> newStack = new Stack<>();
        for (Persona persona: personas) {
            newStack.push(persona);
        }
        return newStack;
    }

    /**
     * Este método actualiza la tabla volviendo a imprimir todos los valores que haya dentro de la lista
     */
    public void updateTable(){
        model = new DefaultTableModel(new String []{"ID","Nombre","Dirección","Teléfono","Edad"}, contactos.size());
        tableContactos.setModel(model);
        if (!contactos.isEmpty()){
            int i = 0;
            for (Persona persona: contactos) {
                tableContactos.setValueAt(persona.getId(), i, 0);
                tableContactos.setValueAt(persona.getNombre(), i, 1);
                tableContactos.setValueAt(persona.getDireccion(), i, 2);
                tableContactos.setValueAt(persona.getTelefono(), i, 3);
                tableContactos.setValueAt(persona.getEdad(), i, 4);
                i++;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tabla vacía");
        }
    }

    /**
     * Este método actualiza la tabla volviendo a imprimir todos los valores que haya dentro de una pila pasada por parámetro
     * @param pila
     */
    public void updateTable(Stack<Persona> pila){
        Iterator<Persona> iterator = pila.iterator();
        Persona persona;
        model = new DefaultTableModel(new String []{"ID","Nombre","Dirección","Teléfono","Edad"}, pila.size());
        tableContactos.setModel(model);
        if (!pila.isEmpty()){
            int i = 0;
            while(iterator.hasNext()){
                persona = iterator.next();
                tableContactos.setValueAt(persona.getId(), i, 0);
                tableContactos.setValueAt(persona.getNombre(), i, 1);
                tableContactos.setValueAt(persona.getDireccion(), i, 2);
                tableContactos.setValueAt(persona.getTelefono(), i, 3);
                tableContactos.setValueAt(persona.getEdad(), i, 4);
                i++;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tabla vacía");
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
            java.util.logging.Logger.getLogger(SelectFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }

    // Declaración de variables
    JTable tableContactos;
    JMenuItem ascAddress;
    JMenuItem ascAge;
    JMenuItem ascId;
    JMenuItem ascName;
    JMenuItem ascPhone;
    JButton btAddContact;
    JButton btDeleteContact;
    JMenuItem descAddress;
    JMenuItem descAge;
    JMenuItem descId;
    JMenuItem descName;
    JMenuItem descPhone;
    JMenuItem itemClose;
    JMenuItem itemOpen;
    JLabel jLabel3;
    JMenu orderMenu;
    JMenu ascMenu;
    JMenu descMenu;
    JMenuBar jMenuBar1;
    JScrollPane jScrollPane1;
    JMenu menuEdit;
    JMenu menuFile;
    // Fin de la declaración de variables
}
