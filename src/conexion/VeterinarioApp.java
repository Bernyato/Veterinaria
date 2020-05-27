package conexion;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSeparator;

public class VeterinarioApp {
	static Connection con;
	public Statement sentencia;

	static String driver = "net.ucanaccess.jdbc.UcanaccessDriver";

	static String url = "jdbc:ucanaccess://D:\\1DAW3\\BaseDeDatos\\Veterinaria\\Veterinaria.accdb";

	public JFrame frame;
	public DefaultTableModel n;
	public JTable table;
	public JTextField txtIdmascota;
	public JTextField txtNumcliente;
	public JTextField txtTipoDeAnimal;
	public JTextField txtNombre;
	public JTextField txtRaza;
	public JTextField txtSexo;
	public JTextField txtFechaDeNacimiento;
	public JComboBox<String> comboBox = new JComboBox<String>();
	public JComboBox<String> comboBoxTipoDeVisita = new JComboBox<String>();
	public ArrayList<String> Clientes = new ArrayList<>();
	public JTable tableVacunas;
	public JTable tablePeso;
	public JButton btnEditarRegistro;
	public JPanel panelMascotas;
	public JPanel panelPrincipal;
	public JPanel panelClientes;
	public JPanel panelConsulta;
	public JTextField txtNombreCliente;
	public JTextField txtDireccion;
	public JTextField txtCiudad;
	public JTextField txtTelefono;
	public JTextField txtUltimaVisita;
	public JTextField txtDescuento;
	public JTable tableClientes;
	public JScrollPane scrollPane_4;
	public JTable tableMascotasDeClientes;
	public JTextField txtNumerocliente;
	public Date fecha;
	private JLabel lblNombre;
	private JLabel lblDireccion;
	private JLabel lblCiudad;
	private JLabel lblTelefono;
	private JLabel lblUltimaVisita;
	private JLabel lblDescuento;
	private JButton btnNuevoCliente;
	private JButton btnEditarCliente;
	private JButton btnVaciarRegistro_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnVisitas;
	private JPanel panelVisitas;
	private JButton buttonVolver;
	private JTable table_1;
	private JTable table_2;
	private JTextField txtIdmascotac;
	private JTextField txtNumclientec;
	private JTextField txtTipoanimalc;
	private JTextField txtNombrec;
	private JTextField txtRazac;
	private JTextField txtSexoc;
	private JTextField txtFechanacimientoc;
	private JTextField txtInsertarpeso;
	private JLabel lblFecha;
	private JPanel panelRutinario;
	private JLabel lblPeso;
	private JTextField txtEnfermedad;
	private JTextField txtProximavacuna;
	private JLabel lblEnfermedad;
	private JLabel lblProximaFecha;
	private JSeparator separator_1;
	private JLabel lblPesoActual;
	private JPanel panelUrgencias;
	private JTextField txtFactura;
	private JLabel lblTotal;
	private JTextField txtDetalles;
	private JLabel lblDetalles;
	private JButton btnTratamiento;
	private JButton btnMedicacion;
	private JPanel panelTratamiento;
	private JLabel lblInfo;
	private JTextField txtInfoTratamiento;
	private JPanel panelMedicamento;
	private JTextField txtInfoMedicacion;
	private JLabel lblInfo_1;
	private JLabel lblNombre_2;
	private JComboBox<String> comboBoxCodigoMedicacion;
	private JComboBox<String> comboBoxCodigoTratamiento;
	private JLabel lblNombre_3;
	private JTextField txtCausaUrgencia;
	private JTextField txtDiagnosticoUrgencia;
	private JButton btnContinuar;
	private JScrollPane scrollPane_7;
	private JTable table_3;
	private boolean urgencias;

	/**
	 * Launch the application.
	 */

	public void conectatarBaseDatos() {
		try {
			Class.forName(driver); /* Linea que carga el driver */
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar Dirver");
		}
		try {
			con = DriverManager.getConnection(url);
			System.out.println("OK");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la dirección de la base de datos");
		}
		try {
			sentencia = con.createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al crear la conexión con la base de datos");
		}
	}

	public static Connection obtenerConexion() {

		try {

			Class.forName(driver);

			con = DriverManager.getConnection(url);

			System.out.println("Conexion correcta");

		} catch (Exception e) {
			e.printStackTrace();
			con = null;
		}
		return con;
	}

	public static void main(String[] args) {
		// url = "jdbc:ucanaccess://" + JOptionPane.showInputDialog("Introduce
		// la ubicación de la base de datos");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VeterinarioApp window = new VeterinarioApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VeterinarioApp() {
		initialize();
		panelMascotas.setVisible(false);
		panelClientes.setVisible(false);
		panelVisitas.setVisible(false);
		panelConsulta.setVisible(false);
		panelTratamiento.setVisible(false);
		panelMedicamento.setVisible(false);
		scrollPane_7.setVisible(false);
		conectatarBaseDatos();

		rellenarComboboxDueños();
		cargarTiposDeVisita();
		cargarCBMedicacion();

		cargarCBTratamiento();

		cargarDatos();
		cargarClientes();

	}

	private void cargarCBTratamiento() {
		String sql = "SELECT CodigoTratamiento FROM Tratamiento";

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea
																// que
																// ejecuta
																// la
																// consulta
																// sql
																// y
																// almacena
																// los
																// datos
																// en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta
										// obtenida
				comboBoxCodigoTratamiento.addItem(resultado.getString("CodigoTratamiento"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void cargarClientes() {

		String datos[] = new String[7]; // Variable que almacena los datos de la
										// consulta
		String sql = "select * from Clientes";

		tableClientes.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "IdCliente", "Nombre",
				"Direccion", "Ciudad", "Telefono", "Ultima Visita", "Descuento" }));
		table_1.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "IdCliente", "Nombre", "Direccion",
				"Ciudad", "Telefono", "Ultima Visita", "Descuento" }));

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea que
																// ejecuta la
																// consulta sql
																// y almacena
																// los datos en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta obtenida
				datos[0] = resultado.getString("IdCliente");
				datos[1] = resultado.getString("Nombre");
				datos[2] = resultado.getString("Dirección");
				datos[3] = resultado.getString("Ciudad");
				datos[4] = resultado.getString("Telefono");
				datos[5] = resultado.getString("UltimaVisita");
				datos[6] = resultado.getString("Descuento");

				n = (DefaultTableModel) tableClientes.getModel();
				n.addRow(datos);

				n = (DefaultTableModel) table_1.getModel();
				n.addRow(datos);
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
		}

	}

	private void rellenarComboboxDueños() {
		cargarNombreClientes();

	}

	private void cargarNombreClientes() {

		String sql = "select Nombre from Clientes"; // Consulta sql

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea que
																// ejecuta la
																// consulta sql
																// y almacena
																// los datos en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta obtenida
				comboBox.addItem(resultado.getString(1));
				Clientes.add(resultado.getString(1));
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar la lista de nombres de Dueños\n" + ex);
		}

	}

	private void cargarTiposDeVisita() {

		String sql = "select Tipo from TipoDeVisita"; // Consulta sql

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea que
																// ejecuta la
																// consulta sql
																// y almacena
																// los datos en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta obtenida
				comboBoxTipoDeVisita.addItem(resultado.getString(1));
				Clientes.add(resultado.getString(1));
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar la lista de nombres de Dueños\n" + ex);
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 870, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panelMascotas = new JPanel();
		panelMascotas.setBounds(0, 0, 852, 465);

		JButton btnCrearRegistro = new JButton("Crear registro");
		btnCrearRegistro.setBounds(692, 350, 129, 25);
		btnCrearRegistro.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (txtNumcliente.getText().equals("") || txtRaza.getText().equals("")
						|| txtTipoDeAnimal.getText().equals("") || txtSexo.getText().equals("")
						|| txtNombre.getText().equals("") || txtFechaDeNacimiento.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Rellene los campos");
				else
					try {

						String sql = "insert into Mascotas (NumCliente, TipoAnimal, Nombre, Raza, Sexo, FechaNacimiento) values (?,?,?,?,?,?)";

						con = obtenerConexion();

						PreparedStatement pst = con.prepareStatement(sql);

						pst.setInt(1, Integer.parseInt(txtNumcliente.getText()));

						pst.setString(2, txtTipoDeAnimal.getText());
						pst.setString(3, txtNombre.getText());
						pst.setString(4, txtRaza.getText());
						pst.setString(5, txtSexo.getText());
						pst.setString(6, txtFechaDeNacimiento.getText());

						int x = pst.executeUpdate();

						if (x > 0)
							JOptionPane.showMessageDialog(null, "Registrado!");
						else
							JOptionPane.showMessageDialog(null, "No registrado!");

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				cargarDatos();
				limparRegistroMascotas();

			}

		});

		btnEditarRegistro = new JButton("Editar registro");
		btnEditarRegistro.setBounds(692, 307, 129, 25);
		btnEditarRegistro.setEnabled(false);
		btnEditarRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarMascota();

			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 249, 654, 188);

		txtNumcliente = new JTextField();
		txtNumcliente.setBounds(223, 64, 116, 22);
		txtNumcliente.setEditable(false);
		txtNumcliente.setText("NumCliente");
		txtNumcliente.setColumns(10);

		txtTipoDeAnimal = new JTextField();
		txtTipoDeAnimal.setBounds(223, 93, 116, 22);
		txtTipoDeAnimal.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setBounds(223, 122, 116, 22);
		txtNombre.setColumns(10);

		txtRaza = new JTextField();
		txtRaza.setBounds(223, 151, 116, 22);
		txtRaza.setColumns(10);

		txtSexo = new JTextField();
		txtSexo.setBounds(223, 180, 116, 22);
		txtSexo.setColumns(10);

		txtFechaDeNacimiento = new JTextField();
		txtFechaDeNacimiento.setBounds(223, 209, 238, 22);
		txtFechaDeNacimiento.setColumns(10);
		comboBox.setBounds(494, 33, 150, 24);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarDatos();

			}
		});

		JLabel lblIdcliente = new JLabel("Id_Cliente");
		lblIdcliente.setBounds(57, 67, 57, 16);

		JLabel lblTipoDeAnimal = new JLabel("Tipo de Animal");
		lblTipoDeAnimal.setBounds(57, 96, 86, 16);

		JLabel lblNombreDelAnimal = new JLabel("Nombre del Animal");
		lblNombreDelAnimal.setBounds(57, 125, 109, 16);

		JLabel lblRaza = new JLabel("Raza");
		lblRaza.setBounds(57, 154, 28, 16);

		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(57, 183, 57, 16);

		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento");
		lblFechaDeNacimiento.setBounds(57, 215, 118, 16);

		JLabel lblDueo = new JLabel("Due\u00F1o:");
		lblDueo.setBounds(420, 37, 41, 16);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(494, 83, 327, 64);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(494, 167, 327, 64);

		JLabel lblVacunas = new JLabel("Vacunas:");
		lblVacunas.setBounds(394, 93, 67, 16);

		JLabel lblPesos = new JLabel("Pesos:");
		lblPesos.setBounds(408, 175, 53, 16);

		JButton btnVaciarRegistro = new JButton("Vaciar registro");
		btnVaciarRegistro.setBounds(692, 264, 129, 25);
		btnVaciarRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				txtTipoDeAnimal.setText("");
				txtNombre.setText("");
				txtRaza.setText("");
				txtSexo.setText("");
				txtFechaDeNacimiento.setText("");
			}
		});

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(692, 393, 129, 25);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMascotas.setVisible(false);
				panelPrincipal.setVisible(true);
			}
		});

		tablePeso = new JTable();
		tablePeso.setEnabled(false);
		tablePeso.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Fecha", "Peso" }));
		scrollPane_2.setViewportView(tablePeso);

		tableVacunas = new JTable();
		tableVacunas.setEnabled(false);
		tableVacunas.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Fecha", "Enfermedad", "ProximaVacuna" }));
		scrollPane_1.setViewportView(tableVacunas);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				tableMascotaSelect(evt);
			}
		});

		panelConsulta = new JPanel();
		panelConsulta.setBounds(0, 0, 852, 459);
		frame.getContentPane().add(panelConsulta);
		panelConsulta.setLayout(null);

		JButton btnVolver_2 = new JButton("Volver");
		btnVolver_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelVisitas.setVisible(true);
				panelConsulta.setVisible(false);

				btnContinuar.setEnabled(false);

				comboBoxCodigoMedicacion.setSelectedItem(comboBoxCodigoMedicacion.getItemAt(0));
				comboBoxCodigoTratamiento.setSelectedItem(comboBoxCodigoTratamiento.getItemAt(0));
			}
		});
		btnVolver_2.setBounds(743, 421, 97, 25);
		panelConsulta.add(btnVolver_2);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecha = new Date();
				lblFecha.setText(fecha.toString());
				insertarNuevaVisita();
				enviarDetallesCita();

				if (comboBoxTipoDeVisita.getSelectedItem().toString().equals("Rutinario")) {
					enviarPesos();
					enviarVacuna();
				} else if (comboBoxTipoDeVisita.getSelectedItem().toString().equals("Urgencias")) {
					enviarUrgencias();
				}

				txtInsertarpeso.setText("");
				txtProximavacuna.setText("");
				txtEnfermedad.setText("");
				txtFactura.setText("");
				txtDetalles.setText("");

				comboBoxCodigoMedicacion.setSelectedItem(comboBoxCodigoMedicacion.getItemAt(0));
				comboBoxCodigoTratamiento.setSelectedItem(comboBoxCodigoTratamiento.getItemAt(0));

			}
		});
		btnEnviar.setBounds(743, 383, 97, 25);
		panelConsulta.add(btnEnviar);

		txtIdmascotac = new JTextField();
		txtIdmascotac.setEditable(false);
		txtIdmascotac.setText("IdMascota_C");
		txtIdmascotac.setBounds(35, 46, 53, 22);
		panelConsulta.add(txtIdmascotac);
		txtIdmascotac.setColumns(10);

		txtNumclientec = new JTextField();
		txtNumclientec.setEditable(false);
		txtNumclientec.setText("NumCliente_C");
		txtNumclientec.setBounds(100, 46, 53, 22);
		panelConsulta.add(txtNumclientec);
		txtNumclientec.setColumns(10);

		txtTipoanimalc = new JTextField();
		txtTipoanimalc.setEditable(false);
		txtTipoanimalc.setText("TipoAnimal_C");
		txtTipoanimalc.setBounds(165, 46, 116, 22);
		panelConsulta.add(txtTipoanimalc);
		txtTipoanimalc.setColumns(10);

		txtNombrec = new JTextField();
		txtNombrec.setEditable(false);
		txtNombrec.setText("Nombre_C");
		txtNombrec.setBounds(293, 46, 116, 22);
		panelConsulta.add(txtNombrec);
		txtNombrec.setColumns(10);

		txtRazac = new JTextField();
		txtRazac.setEditable(false);
		txtRazac.setText("Raza_C");
		txtRazac.setBounds(421, 46, 116, 22);
		panelConsulta.add(txtRazac);
		txtRazac.setColumns(10);

		txtSexoc = new JTextField();
		txtSexoc.setEditable(false);
		txtSexoc.setText("Sexo_C");
		txtSexoc.setBounds(549, 46, 38, 22);
		panelConsulta.add(txtSexoc);
		txtSexoc.setColumns(10);

		txtFechanacimientoc = new JTextField();
		txtFechanacimientoc.setEditable(false);
		txtFechanacimientoc.setText("FechaNacimiento_C");
		txtFechanacimientoc.setBounds(599, 46, 116, 22);
		panelConsulta.add(txtFechanacimientoc);
		txtFechanacimientoc.setColumns(10);

		JLabel lblIdm = new JLabel("ID_M");
		lblIdm.setBounds(35, 23, 56, 16);
		panelConsulta.add(lblIdm);

		JLabel lblIdc = new JLabel("ID_C");
		lblIdc.setBounds(100, 23, 56, 16);
		panelConsulta.add(lblIdc);

		JLabel lblTipoDeAnimal_1 = new JLabel("Tipo de Animal");
		lblTipoDeAnimal_1.setBounds(168, 23, 102, 16);
		panelConsulta.add(lblTipoDeAnimal_1);

		JLabel lblNombreDeMascota = new JLabel("Nombre de Mascota");
		lblNombreDeMascota.setBounds(293, 23, 116, 16);
		panelConsulta.add(lblNombreDeMascota);

		JLabel lblRaza_1 = new JLabel("Raza");
		lblRaza_1.setBounds(421, 23, 56, 16);
		panelConsulta.add(lblRaza_1);

		JLabel lblSexo_1 = new JLabel("Sexo");
		lblSexo_1.setBounds(549, 23, 56, 16);
		panelConsulta.add(lblSexo_1);

		JLabel lblFechaDeNacimiento_1 = new JLabel("Fecha de Nacimiento");
		lblFechaDeNacimiento_1.setBounds(599, 23, 131, 16);
		panelConsulta.add(lblFechaDeNacimiento_1);

		comboBoxTipoDeVisita = new JComboBox<String>();
		comboBoxTipoDeVisita.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				fecha = new Date();
				if (arg0.getItem().toString().equals("Rutinario")) {
					lblFecha.setText(fecha.toString());
					panelRutinario.setVisible(true);
					panelUrgencias.setVisible(false);
				} else if (arg0.getItem().toString().equals("Urgencias")) {
					panelRutinario.setVisible(false);
					panelUrgencias.setVisible(true);
				}
			}
		});
		comboBoxTipoDeVisita.setBounds(727, 46, 113, 22);
		panelConsulta.add(comboBoxTipoDeVisita);

		lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(35, 425, 247, 16);
		panelConsulta.add(lblFecha);

		JLabel lblTipoDeVisita = new JLabel("Tipo de Visita");
		lblTipoDeVisita.setBounds(727, 23, 97, 16);
		panelConsulta.add(lblTipoDeVisita);

		panelUrgencias = new JPanel();
		panelUrgencias.setBounds(12, 81, 467, 327);
		panelConsulta.add(panelUrgencias);
		panelUrgencias.setLayout(null);

		txtCausaUrgencia = new JTextField();
		txtCausaUrgencia.setBounds(117, 100, 304, 22);
		panelUrgencias.add(txtCausaUrgencia);
		txtCausaUrgencia.setColumns(10);

		JLabel lblCausaDeLa = new JLabel("Causa de la Urgencia");
		lblCausaDeLa.setBounds(64, 71, 149, 16);
		panelUrgencias.add(lblCausaDeLa);

		txtDiagnosticoUrgencia = new JTextField();
		txtDiagnosticoUrgencia.setBounds(117, 241, 304, 22);
		panelUrgencias.add(txtDiagnosticoUrgencia);
		txtDiagnosticoUrgencia.setColumns(10);

		JLabel lblDiagnosticoDeLa = new JLabel("Diagnostico de la urgencia");
		lblDiagnosticoDeLa.setBounds(64, 212, 169, 16);
		panelUrgencias.add(lblDiagnosticoDeLa);

		panelRutinario = new JPanel();
		panelRutinario.setBounds(12, 79, 469, 329);
		panelConsulta.add(panelRutinario);
		panelRutinario.setLayout(null);

		txtInsertarpeso = new JTextField();
		txtInsertarpeso.setBounds(219, 61, 116, 22);
		panelRutinario.add(txtInsertarpeso);
		txtInsertarpeso.setColumns(10);

		lblPeso = new JLabel("Peso:");
		lblPeso.setBounds(46, 29, 56, 16);
		panelRutinario.add(lblPeso);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 110, 468, 7);
		panelRutinario.add(separator);

		JLabel lblVacunas_1 = new JLabel("Vacunas:");
		lblVacunas_1.setBounds(46, 125, 56, 16);
		panelRutinario.add(lblVacunas_1);

		txtEnfermedad = new JTextField();
		txtEnfermedad.setBounds(219, 190, 116, 22);
		panelRutinario.add(txtEnfermedad);
		txtEnfermedad.setColumns(10);

		txtProximavacuna = new JTextField();
		txtProximavacuna.setBounds(219, 240, 192, 22);
		panelRutinario.add(txtProximavacuna);
		txtProximavacuna.setColumns(10);

		lblEnfermedad = new JLabel("Enfermedad");
		lblEnfermedad.setBounds(107, 193, 91, 16);
		panelRutinario.add(lblEnfermedad);

		lblProximaFecha = new JLabel("Proxima fecha");
		lblProximaFecha.setBounds(107, 243, 123, 16);
		panelRutinario.add(lblProximaFecha);

		separator_1 = new JSeparator();
		separator_1.setBounds(0, 13, 469, 19);
		panelRutinario.add(separator_1);

		lblPesoActual = new JLabel("Peso actual");
		lblPesoActual.setBounds(107, 64, 69, 16);
		panelRutinario.add(lblPesoActual);

		txtFactura = new JTextField();
		txtFactura.setBounds(599, 384, 116, 22);
		panelConsulta.add(txtFactura);
		txtFactura.setColumns(10);

		lblTotal = new JLabel("Total:");
		lblTotal.setBounds(524, 387, 56, 16);
		panelConsulta.add(lblTotal);

		txtDetalles = new JTextField();
		txtDetalles.setBounds(600, 333, 240, 22);
		panelConsulta.add(txtDetalles);
		txtDetalles.setColumns(10);

		lblDetalles = new JLabel("Detalles:");
		lblDetalles.setBounds(524, 336, 56, 16);
		panelConsulta.add(lblDetalles);

		btnTratamiento = new JButton("Tratamiento");
		btnTratamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelTratamiento.isVisible())
					panelTratamiento.setVisible(false);
				else {
					panelMedicamento.setVisible(false);
					panelTratamiento.setVisible(true);
				}
			}
		});
		btnTratamiento.setBounds(544, 295, 113, 25);
		panelConsulta.add(btnTratamiento);

		btnMedicacion = new JButton("Medicacion");
		btnMedicacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelMedicamento.isVisible())
					panelMedicamento.setVisible(false);
				else {
					panelTratamiento.setVisible(false);
					panelMedicamento.setVisible(true);
				}

			}
		});
		btnMedicacion.setBounds(699, 295, 113, 25);
		panelConsulta.add(btnMedicacion);

		panelTratamiento = new JPanel();
		panelTratamiento.setBounds(505, 95, 335, 187);
		panelConsulta.add(panelTratamiento);
		panelTratamiento.setLayout(null);

		lblInfo = new JLabel("Info:");
		lblInfo.setBounds(40, 117, 56, 16);
		panelTratamiento.add(lblInfo);

		txtInfoTratamiento = new JTextField();
		txtInfoTratamiento.setEditable(false);
		txtInfoTratamiento.setBounds(100, 114, 187, 22);
		panelTratamiento.add(txtInfoTratamiento);
		txtInfoTratamiento.setColumns(10);

		comboBoxCodigoTratamiento = new JComboBox<String>();
		comboBoxCodigoTratamiento.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				cargarTxtTratamiento(e);
			}
		});
		comboBoxCodigoTratamiento.setBounds(115, 40, 172, 22);
		panelTratamiento.add(comboBoxCodigoTratamiento);

		lblNombre_3 = new JLabel("Nombre:");
		lblNombre_3.setBounds(40, 43, 56, 16);
		panelTratamiento.add(lblNombre_3);

		panelMedicamento = new JPanel();
		panelMedicamento.setBounds(505, 95, 335, 187);
		panelConsulta.add(panelMedicamento);
		panelMedicamento.setLayout(null);

		txtInfoMedicacion = new JTextField();
		txtInfoMedicacion.setEditable(false);
		txtInfoMedicacion.setColumns(10);
		txtInfoMedicacion.setBounds(100, 118, 187, 22);
		panelMedicamento.add(txtInfoMedicacion);

		lblInfo_1 = new JLabel("Info:");
		lblInfo_1.setBounds(43, 121, 56, 16);
		panelMedicamento.add(lblInfo_1);

		lblNombre_2 = new JLabel("Nombre:");
		lblNombre_2.setBounds(43, 43, 56, 16);
		panelMedicamento.add(lblNombre_2);

		comboBoxCodigoMedicacion = new JComboBox<String>();
		comboBoxCodigoMedicacion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				cargarTxtMedicacion(arg0);

			}
		});
		comboBoxCodigoMedicacion.setBounds(115, 40, 172, 22);
		panelMedicamento.add(comboBoxCodigoMedicacion);

		panelVisitas = new JPanel();
		panelVisitas.setBounds(0, 0, 852, 459);
		frame.getContentPane().add(panelVisitas);
		panelVisitas.setLayout(null);

		buttonVolver = new JButton("Volver");
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelVisitas.setVisible(false);
				panelPrincipal.setVisible(true);
			}
		});
		buttonVolver.setBounds(743, 421, 97, 25);
		panelVisitas.add(buttonVolver);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(12, 65, 828, 149);
		panelVisitas.add(scrollPane_5);

		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {

				int fila = table_1.rowAtPoint(evt.getPoint());
				int id;
				try {
					id = Integer.parseInt(table_1.getValueAt(fila, 0).toString());
				} catch (Exception e) {

					id = 1;
				}

				String datos[] = new String[7]; // Variable que almacena los
												// datos de la
				// consulta
				String sql = "select * from Mascotas where NumCliente=" + id
						+ " order by IdMascota" /*
												 * + Integer.parseInt((String)
												 * comboBox.getSelectedItem())
												 */; // Consulta
				// sql
				// where
				// idcliente
				// 1
				// String sql = "select * from Mascotas "; //Consulta sql

				table_2.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "IdMascota", "NumCliente",
						"TipoAnimal", "Nombre", "Raza", "Sexo", "FechaNacimiento" }));

				try {

					ResultSet resultado = sentencia.executeQuery(sql); // Linea
																		// que
																		// ejecuta
																		// la
																		// consulta
																		// sql
																		// y
																		// almacena
																		// los
																		// datos
																		// en
																		// resultado

					while (resultado.next()) { // Bucle que recorre la consulta
												// obtenida
						datos[0] = resultado.getString("IdMascota");
						datos[1] = resultado.getString("NumCliente");
						datos[2] = resultado.getString("TipoAnimal");
						datos[3] = resultado.getString("Nombre");
						datos[4] = resultado.getString("Raza");
						datos[5] = resultado.getString("Sexo");
						datos[6] = resultado.getString("FechaNacimiento");

						n = (DefaultTableModel) table_2.getModel();
						n.addRow(datos);
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
				}

			}
		});
		scrollPane_5.setViewportView(table_1);

		btnContinuar = new JButton("Continuar");
		btnContinuar.setEnabled(false);
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelVisitas.setVisible(false);
				fecha = new Date();
				lblFecha.setText(fecha.toString());
				panelConsulta.setVisible(true);
			}
		});
		btnContinuar.setBounds(743, 383, 97, 25);
		panelVisitas.add(btnContinuar);

		JLabel lblVeterinaria_1 = new JLabel("Veterinaria  Abere");
		lblVeterinaria_1.setFont(new Font("Vivaldi", Font.BOLD, 40));
		lblVeterinaria_1.setBounds(393, 13, 296, 49);
		panelVisitas.add(lblVeterinaria_1);

		JLabel lblSeleccionaElDueo = new JLabel("Selecciona el due\u00F1o:");
		lblSeleccionaElDueo.setBounds(30, 36, 162, 16);
		panelVisitas.add(lblSeleccionaElDueo);

		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(12, 308, 688, 138);
		panelVisitas.add(scrollPane_6);

		table_2 = new JTable();
		table_2.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				btnContinuar.setEnabled(true);
				transefrirDatosAConsulta(e);
			}
		});
		scrollPane_6.setViewportView(table_2);

		JLabel lblSeleccionaLaMascota = new JLabel("Selecciona la mascota:");
		lblSeleccionaLaMascota.setBounds(30, 279, 144, 16);
		panelVisitas.add(lblSeleccionaLaMascota);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "IdMascota", "NumCliente", "TipoAnimal", "Nombre", "Raza", "Sexo", "FechaNacimiento" }));

		scrollPane.setViewportView(table);
		frame.getContentPane().add(panelMascotas);
		panelMascotas.setLayout(null);
		panelMascotas.add(scrollPane);
		panelMascotas.add(btnVolver);
		panelMascotas.add(btnCrearRegistro);
		panelMascotas.add(btnEditarRegistro);

		scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(473, 67, 362, 169);
		panelMascotas.add(scrollPane_7);

		table_3 = new JTable();
		table_3.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Causa", "Diagnostico", "Fecha" }));
		scrollPane_7.setViewportView(table_3);
		panelMascotas.add(btnVaciarRegistro);
		panelMascotas.add(lblIdcliente);
		panelMascotas.add(lblTipoDeAnimal);
		panelMascotas.add(lblNombreDelAnimal);
		panelMascotas.add(lblRaza);
		panelMascotas.add(lblSexo);
		panelMascotas.add(lblFechaDeNacimiento);
		panelMascotas.add(lblDueo);
		panelMascotas.add(txtFechaDeNacimiento);
		panelMascotas.add(txtNombre);
		panelMascotas.add(txtTipoDeAnimal);
		panelMascotas.add(lblVacunas);
		panelMascotas.add(txtNumcliente);
		panelMascotas.add(txtSexo);
		panelMascotas.add(txtRaza);
		panelMascotas.add(lblPesos);
		panelMascotas.add(comboBox);
		panelMascotas.add(scrollPane_1);
		panelMascotas.add(scrollPane_2);

		lblNewLabel_1 = new JLabel("ABERE");
		lblNewLabel_1.setFont(new Font("Vivaldi", Font.BOLD, 40));
		lblNewLabel_1.setBounds(77, 13, 230, 49);
		panelMascotas.add(lblNewLabel_1);

		txtIdmascota = new JTextField();
		txtIdmascota.setBounds(223, 34, 116, 22);
		txtIdmascota.setEditable(false);
		txtIdmascota.setVisible(false);
		txtIdmascota.setColumns(10);
		panelMascotas.add(txtIdmascota);

		JButton btnUrgencias = new JButton("Urgencias");
		btnUrgencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (lblVacunas.getText().equals("Vacunas:")) {
					lblVacunas.setText("Urgencias:");
					scrollPane_7.setVisible(true);
					lblPesos.setVisible(false);
					urgencias = true;

				} else {
					lblVacunas.setText("Vacunas:");
					scrollPane_7.setVisible(false);
					lblPesos.setVisible(true);
					urgencias = false;
				}
			}
		});
		btnUrgencias.setBounds(738, 33, 97, 25);
		panelMascotas.add(btnUrgencias);

		panelPrincipal = new JPanel();
		panelPrincipal.setBounds(0, 0, 852, 467);
		frame.getContentPane().add(panelPrincipal);

		JLabel lblVeterinaria = new JLabel("Veterinaria  Abere");
		lblVeterinaria.setFont(new Font("Vivaldi", Font.BOLD, 40));

		JButton btnMascotas = new JButton("Mascotas");
		btnMascotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMascotas.setVisible(true);
				panelPrincipal.setVisible(false);
			}
		});

		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelPrincipal.setVisible(false);
				panelClientes.setVisible(true);
			}
		});

		btnVisitas = new JButton("Visitas");
		btnVisitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelVisitas.setVisible(true);
				panelPrincipal.setVisible(false);
			}
		});
		GroupLayout gl_panelPrincipal = new GroupLayout(panelPrincipal);
		gl_panelPrincipal.setHorizontalGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPrincipal.createSequentialGroup().addGap(278)
						.addComponent(lblVeterinaria, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGap(278))
				.addGroup(Alignment.TRAILING, gl_panelPrincipal.createSequentialGroup().addGap(382)
						.addGroup(gl_panelPrincipal.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnVisitas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 85,
										Short.MAX_VALUE)
								.addComponent(btnClientes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 85,
										Short.MAX_VALUE)
								.addComponent(btnMascotas, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(385)));
		gl_panelPrincipal.setVerticalGroup(gl_panelPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPrincipal.createSequentialGroup().addGap(25).addComponent(lblVeterinaria).addGap(91)
						.addComponent(btnClientes).addPreferredGap(ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
						.addComponent(btnMascotas).addGap(37).addComponent(btnVisitas).addGap(152)));
		panelPrincipal.setLayout(gl_panelPrincipal);

		panelClientes = new JPanel();
		panelClientes.setBounds(0, 0, 852, 467);
		frame.getContentPane().add(panelClientes);

		JButton btnVolver_1 = new JButton("Volver");
		btnVolver_1.setBounds(719, 410, 121, 25);
		btnVolver_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelClientes.setVisible(false);
				panelPrincipal.setVisible(true);
			}
		});

		txtNombreCliente = new JTextField();
		txtNombreCliente.setBounds(110, 40, 116, 22);
		txtNombreCliente.setColumns(10);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(110, 75, 260, 22);
		txtDireccion.setColumns(10);

		txtCiudad = new JTextField();
		txtCiudad.setBounds(110, 110, 116, 22);
		txtCiudad.setColumns(10);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(110, 145, 116, 22);
		txtTelefono.setColumns(10);

		txtUltimaVisita = new JTextField();
		txtUltimaVisita.setBounds(110, 180, 184, 22);
		txtUltimaVisita.setColumns(10);

		txtDescuento = new JTextField();
		txtDescuento.setBounds(110, 215, 116, 22);
		txtDescuento.setColumns(10);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(12, 284, 695, 155);

		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(368, 141, 472, 126);

		tableMascotasDeClientes = new JTable();
		tableMascotasDeClientes
				.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "TipoAnimal", "Nombre", "Raza" }));
		scrollPane_4.setViewportView(tableMascotasDeClientes);

		tableClientes = new JTable();
		tableClientes.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				int fila = tableClientes.rowAtPoint(evt.getPoint());
				btnEditarCliente.setEnabled(true);
				try {
					txtNumerocliente.setText(tableClientes.getValueAt(fila, 0).toString());
				} catch (Exception e) {

					txtIdmascota.setText("");
				}

				int id = Integer.parseInt(txtNumerocliente.getText());

				String datos[] = new String[3]; // Variable que almacena los
												// datos de la
												// consulta
				String sql = "select TipoAnimal, Nombre, Raza from Mascotas where NumCliente=" + id;

				tableMascotasDeClientes.setModel(
						new DefaultTableModel(new Object[][] {}, new String[] { "TipoAnimal", "Nombre", "Raza" }));

				try {

					ResultSet resultado = sentencia.executeQuery(sql); // Linea
																		// que
																		// ejecuta
																		// la
																		// consulta
																		// sql
																		// y
																		// almacena
																		// los
																		// datos
																		// en
																		// resultado

					while (resultado.next()) { // Bucle que recorre la consulta
												// obtenida
						datos[0] = resultado.getString("TipoAnimal");
						datos[1] = resultado.getString("Nombre");
						datos[2] = resultado.getString("Raza");

						n = (DefaultTableModel) tableMascotasDeClientes.getModel();
						n.addRow(datos);
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
				}

				try {
					txtNumerocliente.setText(tableClientes.getValueAt(fila, 0).toString());
				} catch (Exception e) {
					txtNumerocliente.setText("");
				}
				try {
					txtNombreCliente.setText(tableClientes.getValueAt(fila, 1).toString());
				} catch (Exception e) {

					txtNombreCliente.setText("");
				}
				try {
					txtDireccion.setText(tableClientes.getValueAt(fila, 2).toString());
				} catch (Exception e) {

					txtDireccion.setText("");
				}
				try {
					txtCiudad.setText(tableClientes.getValueAt(fila, 3).toString());
				} catch (Exception e) {

					txtCiudad.setText("");
				}
				try {
					txtTelefono.setText(tableClientes.getValueAt(fila, 4).toString());
				} catch (Exception e) {

					txtTelefono.setText("");
				}
				try {
					txtUltimaVisita.setText(tableClientes.getValueAt(fila, 5).toString());
				} catch (Exception e) {

					txtUltimaVisita.setText("");
				}
				try {
					txtDescuento.setText(tableClientes.getValueAt(fila, 6).toString());
				} catch (Exception e) {

					txtDescuento.setText("");
				}

			}
		});
		tableClientes.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "IdCliente", "Nombre",
				"Direccion", "Ciudad", "Telefono", "UltimaVisita", "Descuento" }));
		scrollPane_3.setViewportView(tableClientes);
		panelClientes.setLayout(null);
		panelClientes.add(txtNombreCliente);
		panelClientes.add(txtDireccion);
		panelClientes.add(txtCiudad);
		panelClientes.add(txtTelefono);
		panelClientes.add(txtUltimaVisita);
		panelClientes.add(txtDescuento);
		panelClientes.add(scrollPane_4);
		panelClientes.add(scrollPane_3);
		panelClientes.add(btnVolver_1);

		txtNumerocliente = new JTextField();
		txtNumerocliente.setEditable(false);
		txtNumerocliente.setText("NumeroCliente");
		txtNumerocliente.setBounds(110, 13, 116, 22);
		panelClientes.add(txtNumerocliente);
		txtNumerocliente.setColumns(10);

		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(25, 40, 102, 16);
		panelClientes.add(lblNombre);

		lblDireccion = new JLabel("Direccion");
		lblDireccion.setBounds(25, 78, 102, 16);
		panelClientes.add(lblDireccion);

		lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(25, 113, 102, 16);
		panelClientes.add(lblCiudad);

		lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(25, 148, 102, 16);
		panelClientes.add(lblTelefono);

		lblUltimaVisita = new JLabel("Ultima Visita");
		lblUltimaVisita.setBounds(25, 183, 102, 16);
		panelClientes.add(lblUltimaVisita);

		lblDescuento = new JLabel("Descuento");
		lblDescuento.setBounds(25, 218, 102, 16);
		panelClientes.add(lblDescuento);

		btnNuevoCliente = new JButton("Nuevo cliente");
		btnNuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertatCliente();
			}
		});
		btnNuevoCliente.setBounds(719, 372, 121, 25);
		panelClientes.add(btnNuevoCliente);

		btnEditarCliente = new JButton("Editar cliente");
		btnEditarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					String sql = "update Clientes set Nombre = ?, Dirección = ?, Ciudad = ?, Telefono = ?, UltimaVisita = ?, Descuento = ? where IdCliente ="
							+ Integer.parseInt(txtNumerocliente.getText());

					con = obtenerConexion();

					PreparedStatement pst = con.prepareStatement(sql);

					pst.setString(1, txtNombreCliente.getText());
					pst.setString(2, txtDireccion.getText());
					pst.setString(3, txtCiudad.getText());
					pst.setString(4, txtTelefono.getText());
					pst.setString(5, txtUltimaVisita.getText());
					pst.setString(6, txtDescuento.getText());

					int x = pst.executeUpdate();

					if (x > 0)
						JOptionPane.showMessageDialog(null, "Editado correctamente!");
					else
						JOptionPane.showMessageDialog(null, "No ha sido posible editar el registro!");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				cargarClientes();

			}
		});
		btnEditarCliente.setBounds(719, 334, 121, 25);
		btnEditarCliente.setEnabled(false);
		panelClientes.add(btnEditarCliente);

		btnVaciarRegistro_1 = new JButton("Vaciar registro");
		btnVaciarRegistro_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparRegistroClientes();
			}
		});
		btnVaciarRegistro_1.setBounds(719, 296, 121, 25);
		panelClientes.add(btnVaciarRegistro_1);

		lblNewLabel = new JLabel("ABERE");
		lblNewLabel.setFont(new Font("Vivaldi", Font.BOLD, 40));
		lblNewLabel.setBounds(509, 40, 230, 49);
		panelClientes.add(lblNewLabel);
		txtNumerocliente.setVisible(false);
	}

	protected void enviarUrgencias() {

		try {

			String sql = "insert into Urgencias (IdMascota, Causa, Diagnostico, Fecha) values (?,?,?,?)";

			con = obtenerConexion();

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, txtIdmascotac.getText());
			pst.setString(2, txtCausaUrgencia.getText());
			pst.setString(3, txtDiagnosticoUrgencia.getText() + "Kg");
			pst.setString(4, lblFecha.getText());

			int x = pst.executeUpdate();

			if (x > 0) {
				txtDiagnosticoUrgencia.setText("");
				txtCausaUrgencia.setText("");
				System.out.println("Registrado!");
			} else
				JOptionPane.showMessageDialog(null, "No registrado!");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	protected void cargarTxtTratamiento(ItemEvent e) {
		String sql = "SELECT Tratamiento FROM Tratamiento WHERE CodigoTratamiento='" + e.getItem().toString() + "'";

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea
																// que
																// ejecuta
																// la
																// consulta
																// sql
																// y
																// almacena
																// los
																// datos
																// en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta
										// obtenida
				txtInfoTratamiento.setText(resultado.getString("Tratamiento"));
			}
		} catch (Exception ex) {
			System.out.println("error");
		}

	}

	protected void cargarTxtMedicacion(ItemEvent arg0) {

		String sql = "SELECT Medicación FROM Medicacion WHERE CodigoMedicación='" + arg0.getItem().toString() + "'";

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea
																// que
																// ejecuta
																// la
																// consulta
																// sql
																// y
																// almacena
																// los
																// datos
																// en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta
										// obtenida
				txtInfoMedicacion.setText(resultado.getString("Medicación"));
			}
		} catch (Exception ex) {
			System.out.println("error");
		}

	}

	protected void insertarNuevaVisita() {

		try {

			String sql = "insert into Visita (IdMascota, Fecha, Factura, Detalles) values (?,?,?,?)";

			con = obtenerConexion();

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setInt(1, Integer.parseInt(txtIdmascotac.getText()));
			pst.setString(2, lblFecha.getText());
			pst.setString(3, txtFactura.getText().concat("€"));
			pst.setString(4, txtDetalles.getText());

			int x = pst.executeUpdate();

			if (x > 0) {

				System.out.println("Registrado!");
			} else
				JOptionPane.showMessageDialog(null, "No registrado!");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	protected void enviarDetallesCita() {
		try {

			String sql = "SELECT IdVisita FROM Visita";
			ResultSet resultado = sentencia.executeQuery(sql);
			int idvisita = 0;
			while (resultado.next()) { // Bucle que recorre la consulta

				idvisita = resultado.getInt("IdVisita");
				System.out.println(idvisita);
			}

			sql = "insert into DetalleDeLaVisita (NumeroVisita, TipoVisita, CodigoTratamiento, CodigoMedicacion) values (?,?,?,?)";
			con = obtenerConexion();

			PreparedStatement pst = con.prepareStatement(sql);

			pst = con.prepareStatement(sql);

			pst.setInt(1, idvisita);
			pst.setString(2, comboBoxTipoDeVisita.getSelectedItem().toString());
			pst.setString(3, comboBoxCodigoTratamiento.getSelectedItem().toString());
			pst.setString(4, comboBoxCodigoMedicacion.getSelectedItem().toString());

			int x = pst.executeUpdate();

			if (x > 0) {

				System.out.println("Registrado!");
			} else
				JOptionPane.showMessageDialog(null, "No registrado!");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	protected void enviarPesos() {

		if (!txtInsertarpeso.getText().equals("")) {

			try {

				String sql = "insert into Pesos (IdMascota, Fecha, Peso) values (?,?,?)";

				con = obtenerConexion();

				PreparedStatement pst = con.prepareStatement(sql);

				pst.setString(1, txtIdmascotac.getText());
				pst.setString(2, lblFecha.getText());
				pst.setString(3, txtInsertarpeso.getText() + "Kg");

				int x = pst.executeUpdate();

				if (x > 0) {

					txtInsertarpeso.setText("");
					System.out.println("Registrado!");
				} else
					JOptionPane.showMessageDialog(null, "No registrado!");

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "No has introducido el peso!", "Alerta", 1);
		}

	}

	public void enviarVacuna() {

		if (!txtEnfermedad.getText().equals("") && !txtProximavacuna.getText().equals("")) {
			try {

				String sql = "insert into Vacunas (IdMascota, Fecha, Enfermedad, ProximaVacuna) values (?,?,?,?)";

				con = obtenerConexion();

				PreparedStatement pst = con.prepareStatement(sql);

				pst.setString(1, txtIdmascotac.getText());
				pst.setString(2, lblFecha.getText());
				pst.setString(3, txtEnfermedad.getText());
				pst.setString(4, txtProximavacuna.getText());

				int x = pst.executeUpdate();

				if (x > 0) {
					txtProximavacuna.setText("");
					txtEnfermedad.setText("");
					System.out.println("Registrado!");
				} else
					JOptionPane.showMessageDialog(null, "No registrado!");

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "No has introducido la vacuna!", "Alerta", 1);
		}

	}

	protected void transefrirDatosAConsulta(MouseEvent evt) {

		int fila = table.rowAtPoint(evt.getPoint());

		try {
			txtIdmascotac.setText(table_2.getValueAt(fila, 0).toString());
		} catch (Exception e) {

			txtIdmascotac.setText("");
		}
		try {
			txtNumclientec.setText(table_2.getValueAt(fila, 1).toString());
		} catch (Exception e) {

			txtNumclientec.setText("");
		}
		try {
			txtTipoanimalc.setText(table_2.getValueAt(fila, 2).toString());
		} catch (Exception e) {

			txtTipoanimalc.setText("");
		}
		try {
			txtNombrec.setText(table_2.getValueAt(fila, 3).toString());
		} catch (Exception e) {

			txtNombre.setText("");
		}
		try {
			txtRazac.setText(table_2.getValueAt(fila, 4).toString());
		} catch (Exception e) {

			txtRazac.setText("");
		}
		try {
			txtSexoc.setText(table_2.getValueAt(fila, 5).toString());
		} catch (Exception e) {

			txtSexoc.setText("");
		}
		try {
			txtFechanacimientoc.setText(table_2.getValueAt(fila, 6).toString());
		} catch (Exception e) {

			txtFechanacimientoc.setText("");
		}
	}

	protected void insertatCliente() {

		if (txtNombreCliente.getText().equals("") || txtDireccion.getText().equals("") || txtCiudad.getText().equals("")
				|| txtTelefono.getText().equals("") || txtUltimaVisita.getText().equals("")
				|| txtDescuento.getText().equals(""))
			JOptionPane.showMessageDialog(null, "Rellene los campos");
		else
			try {

				String sql = "insert into Clientes (Nombre, Dirección, Ciudad, Telefono, UltimaVisita, Descuento) values (?,?,?,?,?,?)";

				con = obtenerConexion();

				PreparedStatement pst = con.prepareStatement(sql);

				pst.setString(1, txtNombreCliente.getText());
				pst.setString(2, txtDireccion.getText());
				pst.setString(3, txtCiudad.getText());
				pst.setString(4, txtTelefono.getText());
				pst.setString(5, txtUltimaVisita.getText());
				pst.setString(6, txtDescuento.getText());

				int x = pst.executeUpdate();

				if (x > 0)
					JOptionPane.showMessageDialog(null, "Registrado!");
				else
					JOptionPane.showMessageDialog(null, "No registrado!");

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		comboBox.addItem(txtNombreCliente.getText());
		cargarClientes();
		limparRegistroClientes();

	}

	private void limparRegistroClientes() {
		txtNombreCliente.setText("");
		txtDireccion.setText("");
		txtCiudad.setText("");
		txtTelefono.setText("");
		txtUltimaVisita.setText("");
		txtDescuento.setText("");

	}

	protected void actualizarMascota() {
		try {

			String sql = "update Mascotas set TipoAnimal = ?, Nombre = ?, Raza =?, Sexo = ?, FechaNacimiento = ? where IdMascota ="
					+ Integer.parseInt(txtIdmascota.getText());

			con = obtenerConexion();

			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, txtTipoDeAnimal.getText());
			pst.setString(2, txtNombre.getText());
			pst.setString(3, txtRaza.getText());
			pst.setString(4, txtSexo.getText());
			pst.setString(5, txtFechaDeNacimiento.getText());

			int x = pst.executeUpdate();

			if (x > 0)
				JOptionPane.showMessageDialog(null, "Editado correctamente!");
			else
				JOptionPane.showMessageDialog(null, "No ha sido posible editar el registro!");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		cargarDatos();
	}

	protected void limparRegistroMascotas() {
		txtIdmascota.setText("");

		txtNumcliente.setText("");

		txtTipoDeAnimal.setText("");

		txtNombre.setText("");

		txtRaza.setText("");

		txtSexo.setText("");

		txtFechaDeNacimiento.setText("");

	}

	protected void tableMascotaSelect(MouseEvent evt) {
		btnEditarRegistro.setEnabled(true);
		int fila = table.rowAtPoint(evt.getPoint());

		try {
			txtIdmascota.setText(table.getValueAt(fila, 0).toString());
		} catch (Exception e) {

			txtIdmascota.setText("");
		}
		if (urgencias) {
			cargarUrgencias();
		} else {
			cargarPesos();
			cargarVacunas();
		}

		try {
			txtNumcliente.setText(table.getValueAt(fila, 1).toString());
		} catch (Exception e) {

			txtNumcliente.setText("");
		}
		try {
			txtTipoDeAnimal.setText(table.getValueAt(fila, 2).toString());
		} catch (Exception e) {

			txtTipoDeAnimal.setText("");
		}
		try {
			txtNombre.setText(table.getValueAt(fila, 3).toString());
		} catch (Exception e) {

			txtNombre.setText("");
		}
		try {
			txtRaza.setText(table.getValueAt(fila, 4).toString());
		} catch (Exception e) {

			txtRaza.setText("");
		}
		try {
			txtSexo.setText(table.getValueAt(fila, 5).toString());
		} catch (Exception e) {

			txtSexo.setText("");
		}
		try {
			txtFechaDeNacimiento.setText(table.getValueAt(fila, 6).toString());
		} catch (Exception e) {

			txtFechaDeNacimiento.setText("");
		}
	}

	private void cargarUrgencias() {
		int id = Integer.parseInt(txtIdmascota.getText());

		String datos[] = new String[3]; // Variable que almacena los datos de la
										// consulta
		String sql = "select * from Urgencias where IdMascota=" + id;

		table_3.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Causa", "Diagnostico", "Fecha" }));

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea que
																// ejecuta la
																// consulta sql
																// y almacena
																// los datos en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta obtenida
				datos[0] = resultado.getString("Causa");
				datos[1] = resultado.getString("Diagnostico");
				datos[2] = resultado.getString("Fecha");

				n = (DefaultTableModel) table_3.getModel();
				n.addRow(datos);
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
		}

	}

	public void cargarDatos() {

		int id = Clientes.indexOf(comboBox.getSelectedItem().toString()) + 1;
		txtNumcliente.setText(Integer.toString(id));

		String datos[] = new String[7]; // Variable que almacena los datos de la
										// consulta
		String sql = "select * from Mascotas where NumCliente=" + id
				+ " order by IdMascota" /*
										 * + Integer.parseInt((String)
										 * comboBox.getSelectedItem())
										 */; // Consulta
		// sql
		// where
		// idcliente
		// 1
		// String sql = "select * from Mascotas "; //Consulta sql

		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "IdMascota", "NumCliente", "TipoAnimal", "Nombre", "Raza", "Sexo", "FechaNacimiento" }));

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea que
																// ejecuta la
																// consulta sql
																// y almacena
																// los datos en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta obtenida
				datos[0] = resultado.getString("IdMascota");
				datos[1] = resultado.getString("NumCliente");
				datos[2] = resultado.getString("TipoAnimal");
				datos[3] = resultado.getString("Nombre");
				datos[4] = resultado.getString("Raza");
				datos[5] = resultado.getString("Sexo");
				datos[6] = resultado.getString("FechaNacimiento");

				n = (DefaultTableModel) table.getModel();
				n.addRow(datos);
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
		}

	}

	private void cargarCBMedicacion() {
		String sql = "SELECT CodigoMedicación FROM Medicacion";

		try {

			ResultSet resultado = sentencia.executeQuery(sql);

			while (resultado.next()) {
				comboBoxCodigoMedicacion.addItem(resultado.getString("CodigoMedicación"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void cargarPesos() {
		int id = Integer.parseInt(txtIdmascota.getText());

		String datos[] = new String[2]; // Variable que almacena los datos de la
										// consulta
		String sql = "select * from Pesos where IdMascota=" + id; // Consulta
		// sql
		// where
		// idcliente
		// 1
		// String sql = "select * from Mascotas "; //Consulta sql

		tablePeso.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Fecha", "Peso" }));

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea que
																// ejecuta la
																// consulta sql
																// y almacena
																// los datos en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta obtenida
				datos[0] = resultado.getString("Fecha");
				datos[1] = resultado.getString("Peso");

				n = (DefaultTableModel) tablePeso.getModel();
				n.addRow(datos);
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
		}
	}

	public void cargarVacunas() {
		int id = Integer.parseInt(txtIdmascota.getText());

		String datos[] = new String[3]; // Variable que almacena los datos de la
										// consulta
		String sql = "select * from Vacunas where IdMascota=" + id; // Consulta
		// sql
		// where
		// idcliente
		// 1
		// String sql = "select * from Mascotas "; //Consulta sql

		tableVacunas.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Fecha", "Enfermedad", "ProximaVacuna" }));

		try {

			ResultSet resultado = sentencia.executeQuery(sql); // Linea que
																// ejecuta la
																// consulta sql
																// y almacena
																// los datos en
																// resultado

			while (resultado.next()) { // Bucle que recorre la consulta obtenida
				datos[0] = resultado.getString("Fecha");
				datos[1] = resultado.getString("Enfermedad");
				datos[2] = resultado.getString("ProximaVacuna");

				n = (DefaultTableModel) tableVacunas.getModel();
				n.addRow(datos);
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error al cargar los Datos\n" + ex);
		}
	}
}
