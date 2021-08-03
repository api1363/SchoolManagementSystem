package studentManagement;

public class StudentManagementModel {
	private int studentId;
	private String studentEmail;
	private String studentPassword;
	
	public StudentManagementModel(int studentId, String studentEmail, String studentPassword) {
		super();
		this.studentId = studentId;
		this.studentEmail = studentEmail;
		this.studentPassword = studentPassword;
	}

	public StudentManagementModel(String studentEmail, String studentPassword) {
		super();
		this.studentEmail = studentEmail;
		this.studentPassword = studentPassword;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public String getStudentPassword() {
		return studentPassword;
	}

	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
}
