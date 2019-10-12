package com.trifulcas.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		String cadConexion = "jdbc:mysql://localhost:3306/";
		String bd = "empresa";
		String usuario = "root";
		String pass = "1263";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			Connection con = DriverManager.getConnection(cadConexion + bd, usuario, pass);
			Statement stmt;
			PreparedStatement pstmt;
			ResultSet rs;
			Scanner entrada = new Scanner(System.in);
			//variables generales
			int res, res1, id;
			//variables producto
			String nombre, codigo;
			float precio;
			int idProveedor;
			//variables proveedor
			String nProveedor, direccion, nif;

			do {
				
				System.out.println("\nEscoja opción a tratar:");
				System.out.println("1.- Productos");
				System.out.println("2.- Proveedores");
				System.out.println("0.- Salir");
				res1 = Integer.parseInt(entrada.nextLine());
				
				if(res1!=0) {
					System.out.println("\nEscoja opción:");
				System.out.println("1.- Ver registros");
				System.out.println("2.- Añadir registro");
				System.out.println("3.- Modificar registro");
				System.out.println("4.- Eliminar registro");
				res = Integer.parseInt(entrada.nextLine());
				
				switch (res) {
				case 1:
					stmt = con.createStatement();
					if(res1==1) {
						rs = stmt.executeQuery("select * from producto");
						while (rs.next())
							System.out.println("Id producto:" + rs.getInt("id_producto") + "    Precio:" + rs.getFloat("precio") +  "    Nombre:"  + rs.getString("nombre") +  "    Código:"  + rs.getString("codigo") +  "    Id proveedor:"  + rs.getInt("id_proveedor"));			
					}
					if(res1==2) {
						rs = stmt.executeQuery("select * from proveedor");
						while (rs.next())
							System.out.println("Id proveedor:" + rs.getInt("id_proveedor") + "    NIF:" + rs.getString("nif") +  "    Nombre:"  + rs.getString("nombre") +  "    Dirección:"  + rs.getString("direccion"));			
					}
					break;
				case 2:
					if(res1==1) {
					System.out.println("Introduzca el precio del producto: ");
					precio = Float.parseFloat(entrada.nextLine());
					System.out.println("Introduzca el nombre del producto: ");
					nombre = entrada.nextLine();
					System.out.println("Introduzca el código del producto: ");
					codigo = entrada.nextLine();
					System.out.println("Introduzca el id del proveedor del producto: ");
					idProveedor = Integer.parseInt(entrada.nextLine());
					pstmt = con.prepareStatement("insert into producto (precio, nombre, codigo, id_proveedor)" + " values (?, ?, ?, ?)");
					pstmt.setFloat(1, precio);
					pstmt.setString(2, nombre);
					pstmt.setString(3, codigo);
					pstmt.setInt(4, idProveedor);
					pstmt.execute();
					System.out.println("Producto insertado");
					}
					if(res1==2) {
						System.out.println("Introduzca el nombre del proveedor: ");
						nProveedor = entrada.nextLine();
						System.out.println("Introduzca el NIF del proveedor: ");
						nif = entrada.nextLine();
						System.out.println("Introduzca la dirección del proveedor: ");
						direccion = entrada.nextLine();
						pstmt = con.prepareStatement("insert into proveedor (nif, nombre, direccion)" + " values (?, ?, ?)");
						pstmt.setString(1, nif);
						pstmt.setString(2, nProveedor);
						pstmt.setString(3, direccion);
						pstmt.execute();
						System.out.println("Proveedor insertado");
					}
					break;
				case 3:
					stmt = con.createStatement();
					if(res1==1) {
						System.out.println("Introduzca el id del producto que quiere modificar: ");
						id = Integer.parseInt(entrada.nextLine());
						System.out.println("Introduzca el nuevo nombre del producto: ");
						nombre = entrada.nextLine();
						System.out.println("Introduzca el nuevo código del producto: ");
						codigo = entrada.nextLine();
						System.out.println("Introduzca el nuevo precio del producto: ");
						precio = Float.parseFloat(entrada.nextLine());
						System.out.println("Introduzca el nuevo id del proveedor del producto: ");
						idProveedor = Integer.parseInt(entrada.nextLine());
						pstmt = con.prepareStatement("update producto set precio = ?, nombre = ?, codigo = ?, id_proveedor = ? where id_producto= ?");
						pstmt.setFloat(1, precio);
						pstmt.setString(2, nombre);
						pstmt.setString(3, codigo);
						pstmt.setInt(4, idProveedor);
						pstmt.setInt(5,id);
						pstmt.executeUpdate();
						System.out.println("Producto modificado");
					}
					if(res1==2) {
						System.out.println("Introduzca el id del proveedor que quiere modificar: ");
						id = Integer.parseInt(entrada.nextLine());
						System.out.println("Introduzca el nuevo nombre del proveedor: ");
						nProveedor = entrada.nextLine();
						System.out.println("Introduzca el nuevo NIF del proveedor: ");
						nif = entrada.nextLine();
						System.out.println("Introduzca la nueva dirección del proveedor: ");
						direccion = entrada.nextLine();
						pstmt = con.prepareStatement("update proveedor set nif = ?, nombre = ?, direccion = ? where id_proveedor = ?");
						pstmt.setString(1, nif);
						pstmt.setString(2, nProveedor);
						pstmt.setString(3, direccion);
						pstmt.setInt(4,id);
						pstmt.executeUpdate();
						System.out.println("Proveedor modificado");
					}
					break;
				case 4:
					if(res1==1) {
					System.out.println("Introduzca el id de la producto que quiere eliminar: ");
					id = Integer.parseInt(entrada.nextLine());
					pstmt = con.prepareStatement("delete from producto where id_producto = ?");
					pstmt.setInt(1, id);
					pstmt.execute();
					System.out.println("Producto eliminado");
					}
					if(res1==2) {
					System.out.println("Introduzca el id de la proveedor que quiere eliminar: ");
					id = Integer.parseInt(entrada.nextLine());
					pstmt = con.prepareStatement("delete from proveedor where id_proveedor = ?");
					pstmt.setInt(1, id);
					pstmt.execute();
					System.out.println("Proveedor eliminado");
					}
					break;
				}
				}
			} while (res1 != 0);
			con.close();
			entrada.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		

	}

}
