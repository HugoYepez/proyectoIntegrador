package interfaz;

import modelo.Propiedad;
import modelo.PropiedadesFiltro;
import negocio.InmobiliariaNegocio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private InmobiliariaNegocio negocio;

    // Pestania 2: Datos globales
    private JTable tablaPropiedades;
    private DefaultTableModel modeloTabla;

    // Pestania 3: Tasacion Inteligente y Recomendaciones
    private JTextField txtMetrosCuadrados;
    private JSpinner spinnerHabitaciones;
    private JSpinner spinnerBanios;
    private JComboBox<String> comboTipoCasa;
    private JLabel lblResultadoPrecio;
    private JTable tablaRecomendados;
    private DefaultTableModel modeloTablaRecomendados;

    public VentanaPrincipal() {
        // Inicializar la logica de negocio
        negocio = new InmobiliariaNegocio();
        negocio.inicializarSistema("datos_propiedades_ecuador.csv");

        // Configuracion de la ventana basica
        setTitle("Ecuahomes Analytics v1.0 - Plataforma Corporativa Inmobiliaria");
        setSize(1050, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Contenedor de pestanias
        JTabbedPane pestanias = new JTabbedPane();
        pestanias.setFont(new Font("Segoe UI", Font.BOLD, 13));

        pestanias.addTab("Panel de Bienvenida", crearPanelInicio());
        pestanias.addTab("Base de Datos de Mercado", crearPanelDatos());
        pestanias.addTab("Tasacion Inteligente (AI)", crearPanelPrediccion());

        add(pestanias);
    }

    /**
     * PESTANIA 1: BIENVENIDA EMPRESARIAL
     */
    private JPanel crearPanelInicio() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 247, 250));

        JPanel bannerSuperior = new JPanel(new BorderLayout());
        bannerSuperior.setBackground(new Color(18, 38, 58));
        bannerSuperior.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTituloEmpresa = new JLabel("ECUAHOMES ANALYTICS", JLabel.LEFT);
        lblTituloEmpresa.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTituloEmpresa.setForeground(Color.WHITE);

        JLabel lblSlogan = new JLabel("Soluciones Inteligentes para la Toma de Decisiones Inmobiliarias en Ecuador", JLabel.LEFT);
        lblSlogan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSlogan.setForeground(new Color(173, 189, 206));

        bannerSuperior.add(lblTituloEmpresa, BorderLayout.NORTH);
        bannerSuperior.add(lblSlogan, BorderLayout.SOUTH);
        panelPrincipal.add(bannerSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        panelCentral.setBackground(new Color(245, 247, 250));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel panelLogo = new JPanel(new BorderLayout());
        panelLogo.setBackground(Color.WHITE);
        panelLogo.setBorder(BorderFactory.createLineBorder(new Color(218, 224, 233), 1));

        JLabel lblImagen = new JLabel("", JLabel.CENTER);
        try {
            ImageIcon iconOriginal = new ImageIcon("logo.jpg");
            Image imgEscalada = iconOriginal.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imgEscalada));
        } catch (Exception e) {
            lblImagen.setText("<html><center><b>[ Espacio para logo.png ]</b><br><font color='gray'>Coloca una imagen en la raiz del proyecto para visualizarla aqui</font></center></html>");
        }
        panelLogo.add(lblImagen, BorderLayout.CENTER);
        panelCentral.add(panelLogo);

        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 224, 233), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.fill = GridBagConstraints.HORIZONTAL;
        gbcInfo.gridx = 0;
        gbcInfo.weightx = 1.0;

        JLabel lblBienvenida = new JLabel("Simulacion y Analisis de Costos");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBienvenida.setForeground(new Color(18, 38, 58));
        gbcInfo.gridy = 0;
        gbcInfo.insets = new Insets(0, 0, 15, 0);
        panelInfo.add(lblBienvenida, gbcInfo);

        JTextArea txtDescripcion = new JTextArea(
                "Nuestra plataforma le permite modelar y proyectar el valor estimado de una propiedad en el mercado ecuatoriano de forma automatizada. " +
                        "A traves del analisis estadistico de datos reales, usted podra evaluar escenarios comerciales adaptados a sus requerimientos especificos."
        );
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setBackground(Color.WHITE);
        txtDescripcion.setForeground(new Color(80, 90, 100));
        gbcInfo.gridy = 1;
        gbcInfo.insets = new Insets(0, 0, 25, 0);
        panelInfo.add(txtDescripcion, gbcInfo);

        JLabel lblParametros = new JLabel("Como calcular el costo de su vivienda:");
        lblParametros.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblParametros.setForeground(new Color(18, 38, 58));
        gbcInfo.gridy = 2;
        gbcInfo.insets = new Insets(0, 0, 10, 0);
        panelInfo.add(lblParametros, gbcInfo);

        JLabel lblDim = new JLabel("Metros Cuadrados de Construccion");
        lblDim.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDim.setForeground(new Color(28, 116, 187));
        gbcInfo.gridy = 3;
        gbcInfo.insets = new Insets(0, 5, 2, 0);
        panelInfo.add(lblDim, gbcInfo);

        JLabel lblDimDesc = new JLabel("<html>Defina el area total construida. El metraje es el factor matematico principal que determina la tendencia base del precio de mercado.</html>");
        lblDimDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDimDesc.setForeground(Color.GRAY);
        gbcInfo.gridy = 4;
        gbcInfo.insets = new Insets(0, 5, 12, 0);
        panelInfo.add(lblDimDesc, gbcInfo);

        JLabel lblDist = new JLabel("Distribucion: Habitaciones, Banios y Ambientes");
        lblDist.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDist.setForeground(new Color(46, 139, 87));
        gbcInfo.gridy = 5;
        gbcInfo.insets = new Insets(0, 5, 2, 0);
        panelInfo.add(lblDist, gbcInfo);

        JLabel lblDistDesc = new JLabel("<html>Establezca la cantidad de dormitorios y servicios sanitarios necesarios. El sistema evalua estas variables para clasificar la propiedad y detectar inconsistencias en los costos.</html>");
        lblDistDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDistDesc.setForeground(Color.GRAY);
        gbcInfo.gridy = 6;
        gbcInfo.insets = new Insets(0, 5, 20, 0);
        panelInfo.add(lblDistDesc, gbcInfo);

        JLabel lblNotaNavegacion = new JLabel("<html><i>Sugerencia: Use las pestanias superiores para explorar la base de datos completa o ingresar sus datos en el simulador predictivo.</i></html>");
        lblNotaNavegacion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNotaNavegacion.setForeground(new Color(120, 130, 140));
        gbcInfo.gridy = 7;
        gbcInfo.insets = new Insets(10, 0, 0, 0);
        panelInfo.add(lblNotaNavegacion, gbcInfo);

        panelCentral.add(panelInfo);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        JPanel piePagina = new JPanel(new FlowLayout(FlowLayout.CENTER));
        piePagina.setBackground(new Color(18, 38, 58));
        JLabel lblDerechos = new JLabel("2026 Ecuahomes Analytics. Todos los derechos reservados. Consultoria Tecnologica Inmobiliaria.");
        lblDerechos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDerechos.setForeground(Color.WHITE);
        piePagina.add(lblDerechos);
        panelPrincipal.add(piePagina, BorderLayout.SOUTH);

        return panelPrincipal;
    }

    /**
     * PESTANIA 2: TABLA GLOBAL DE REGISTROS
     */
    private JPanel crearPanelDatos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Monitoreo Global de la Base de Datos Inmobiliaria", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Tipo", "Sector", "Construido m2", "Precio USD", "Referencia", "Dorm.", "Banios", "Antiguedad", "Estado Filtro"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPropiedades = new JTable(modeloTabla);

        actualizarTablaGlobal();

        JScrollPane scrollPane = new JScrollPane(tablaPropiedades);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRefrescar = new JButton("Sincronizar CSV");
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefrescar.addActionListener(e -> actualizarTablaGlobal());
        panelBotones.add(btnRefrescar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * PESTANIA 3: TASACION INTELIGENTE (IA) Y RECOMENDACIONES MULTIVARIABLE
     */
    private JPanel crearPanelPrediccion() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- SUB-PANEL SUPERIOR: FORMULARIO DE REQUERIMIENTOS ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                " Parametros de Busqueda de la Vivienda ", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(18, 38, 58)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Metros Cuadrados
        JLabel lblM2 = new JLabel("Metros Cuadrados (m2):");
        lblM2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        panelFormulario.add(lblM2, gbc);

        txtMetrosCuadrados = new JTextField("120.0");
        txtMetrosCuadrados.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.weightx = 0.3;
        panelFormulario.add(txtMetrosCuadrados, gbc);

        // 2. Tipo de Casa
        JLabel lblTipo = new JLabel("Tipo de Propiedad:");
        lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 2; gbc.weightx = 0.2;
        panelFormulario.add(lblTipo, gbc);

        String[] opcionesCasa = {"Casa", "Departamento", "Quinta"};
        comboTipoCasa = new JComboBox<>(opcionesCasa);
        comboTipoCasa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 3; gbc.weightx = 0.3;
        panelFormulario.add(comboTipoCasa, gbc);

        // 3. Habitaciones (Spinner)
        JLabel lblHab = new JLabel("Numero de Habitaciones:");
        lblHab.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.2;
        panelFormulario.add(lblHab, gbc);

        spinnerHabitaciones = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        spinnerHabitaciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.weightx = 0.3;
        panelFormulario.add(spinnerHabitaciones, gbc);

        // 4. Banios (Spinner)
        JLabel lblBanios = new JLabel("Numero de Banios:");
        lblBanios.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 2; gbc.weightx = 0.2;
        panelFormulario.add(lblBanios, gbc);

        spinnerBanios = new JSpinner(new SpinnerNumberModel(2, 1, 8, 1));
        spinnerBanios.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 3; gbc.weightx = 0.3;
        panelFormulario.add(spinnerBanios, gbc);

        // 5. Boton de Accion
        JButton btnCalcular = new JButton("Calcular Tasacion y Buscar Opciones");
        btnCalcular.setBackground(new Color(46, 139, 87));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4; gbc.weightx = 1.0;
        panelFormulario.add(btnCalcular, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        // --- SUB-PANEL CENTRAL: PRESENTACION DE RESULTADO Y TABLA DE CASAS RECOMENDADAS ---
        JPanel panelResultados = new JPanel(new BorderLayout(0, 10));

        lblResultadoPrecio = new JLabel("Precio Proyectado por IA: $ 0.00 USD", JLabel.CENTER);
        lblResultadoPrecio.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblResultadoPrecio.setForeground(new Color(139, 0, 0));
        panelResultados.add(lblResultadoPrecio, BorderLayout.NORTH);

        // Inicializar tabla de casas que coinciden con el rango de precio
        String[] columnasRec = {"Tipo", "Sector", "Construido m2", "Precio USD", "Dorm.", "Banios", "Antiguedad"};
        modeloTablaRecomendados = new DefaultTableModel(columnasRec, 0);
        tablaRecomendados = new JTable(modeloTablaRecomendados);

        JScrollPane scrollRecomendados = new JScrollPane(tablaRecomendados);
        scrollRecomendados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(218, 224, 233)),
                " Propiedades en Zonas Disponibles dentro del Rango de Presupuesto Proyectado ",
                0, 0, new Font("Segoe UI", Font.ITALIC, 12), Color.GRAY
        ));

        panelResultados.add(scrollRecomendados, BorderLayout.CENTER);
        panelPrincipal.add(panelResultados, BorderLayout.CENTER);

        // EVENTO: Procesar click del boton
        btnCalcular.addActionListener(e -> {
            try {
                double metros = Double.parseDouble(txtMetrosCuadrados.getText().trim());
                if (metros <= 0) {
                    JOptionPane.showMessageDialog(this, "El valor de metros cuadrados debe ser mayor a cero.", "Dato Invalido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int habitaciones = (int) spinnerHabitaciones.getValue();
                int banios = (int) spinnerBanios.getValue();
                String tipoCasa = (String) comboTipoCasa.getSelectedItem();

                // Construcción de objeto Filtro (POO)
                PropiedadesFiltro filtroCliente = new PropiedadesFiltro(metros, habitaciones, banios, tipoCasa);

                // Calculo de la prediccion delegando la responsabilidad a la logica de negocio
                double precioPredicho = negocio.getModeloPrediccion().predecirPrecioMultiple(filtroCliente);
                lblResultadoPrecio.setText(String.format("Precio Proyectado por IA: $ %,.2f USD", precioPredicho));

                // Filtrar y refrescar tabla secundaria en rango de precios (+/- 15%)
                actualizarTablaRecomendados(precioPredicho);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor numerico correcto en el campo de m2.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panelPrincipal;
    }

    private void actualizarTablaGlobal() {
        if (modeloTabla == null) return;
        modeloTabla.setRowCount(0);
        List<Propiedad> propiedades = negocio.getListaPropiedades();

        for (Propiedad p : propiedades) {
            Object[] fila = {
                    p.getTipoPropiedad(), p.getSector(), p.getTotalConstruido(),
                    p.getPrecio(), p.getReferencia(), p.getDormitorios(),
                    p.getBanos(), p.getAntiguedad(), p.getEstadoInspeccion()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void actualizarTablaRecomendados(double precioBase) {
        modeloTablaRecomendados.setRowCount(0);
        List<Propiedad> todas = negocio.getListaPropiedades();

        // Rango de tolerancia comercial (15% por arriba y por abajo)
        double rangoMinimo = precioBase * 0.85;
        double rangoMaximo = precioBase * 1.15;

        for (Propiedad p : todas) {
            if (p.getPrecio() >= rangoMinimo && p.getPrecio() <= rangoMaximo) {
                Object[] fila = {
                        p.getTipoPropiedad(), p.getSector(), p.getTotalConstruido(),
                        p.getPrecio(), p.getDormitorios(), p.getBanos(), p.getAntiguedad()
                };
                modeloTablaRecomendados.addRow(fila);
            }
        }

        if(modeloTablaRecomendados.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron inmuebles registrados exactamente dentro del rango de tolerancia del (+/- 15%) de este precio.", "Sin Stock", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}