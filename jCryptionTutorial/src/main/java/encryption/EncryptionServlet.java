package encryption;

import java.io.IOException;
import java.security.KeyPair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EncryptionServlet
 */
public class EncryptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EncryptionServlet() {

	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("generateKeypair") != null) {
			JCryptionUtil jCryptionUtil = new JCryptionUtil();
			KeyPair keys = (KeyPair) request.getSession().getAttribute("keys");
			if (keys == null) {
				keys = jCryptionUtil.generateKeypair(1024);
				request.getSession().setAttribute("keys", keys);
			}
			StringBuffer out = new StringBuffer();

			String e = JCryptionUtil.getPublicKeyExponent(keys);
			String n = JCryptionUtil.getPublicKeyModulus(keys);
			String md = String.valueOf(JCryptionUtil.getMaxDigits(1024));

			out.append("{\"e\":\"");
			out.append(e);
			out.append("\",\"n\":\"");
			out.append(n);
			out.append("\",\"maxdigits\":\"");
			out.append(md);
			out.append("\"}");

			out.toString();
			response.getOutputStream().print(out.toString().replaceAll("\r", "").replaceAll("\n", "").trim());
		} else {
			response.getOutputStream().print(String.valueOf(false));
		}
	}

}
