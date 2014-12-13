package contenedor;

public class Tri {
	private String idTri;
	private String identificador;
	private String nombre;
	private String foto;
	private String activo;
	private String localizacion;
	private String perdido;
	private String compartido;



	public Tri(String idTri, String identificador, String nombre, String foto, String activo,
			String localizacion, String perdido, String compartido) {
		super();
		this.idTri=idTri;
		this.identificador = identificador;
		this.nombre = nombre;
		this.foto = foto;
		this.activo = activo;
		this.localizacion = localizacion;
		this.perdido = perdido;
		this.compartido = compartido;
	}
	

	public String getIdTri() {
		return idTri;
	}


	public void setIdTri(String idTri) {
		this.idTri = idTri;
	}


	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String getPerdido() {
		return perdido;
	}

	public void setPerdido(String perdido) {
		this.perdido = perdido;
	}

	public String getCompartido() {
		return compartido;
	}

	public void setCompartido(String compartido) {
		this.compartido = compartido;
	}

}
