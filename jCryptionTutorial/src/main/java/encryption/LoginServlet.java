package encryption;

import java.io.IOException;
import java.security.KeyPair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		KeyPair keys = (KeyPair) request.getSession().getAttribute("keys");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username" + username);
		System.out.println("password" + password);
		String decryptedUser = JCryptionUtil.decrypt(username, keys);
		String decryptedPassword = JCryptionUtil.decrypt(password, keys);

		response.getWriter().append("Decrypted user name:" + decryptedUser + "     and decrypted Password :" + decryptedPassword);

	}

}
