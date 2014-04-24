<html>
  <head>
    <script type="text/javascript" src="assets/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="assets/js/security/jquery.jcryption-1.2.min.js"></script>
    <script type="text/javascript">
    	var keys;
    	
    	function getKeys() {
			$.jCryption.getKeys("EncryptionServlet?generateKeypair=true", function(receivedKeys) {
				keys = receivedKeys;
			});
		}

    	function onLoginButtonClicked() {
			var user = $("#login_user").val();
			var password = $("#login_password").val();
			$.jCryption.encrypt(user, keys, function(encrypted) {
				encryptedUser = encrypted;
				$.jCryption.encrypt(password, keys, function(encryptedPasswd) {
					encryptedPassword = encryptedPasswd;
					
					submitLoginRequest();
				});
			});
		}
		
		function submitLoginRequest() {
			sendAjaxRequest("LoginServlet", {
				username : encryptedUser,
				password : encryptedPassword
			}, function(data) {
				if (data.length > 0) {
					$("#login_status").empty();
					$("#login_status").append(data);
				}
			});
		}
		
		function sendAjaxRequest(url, parameters, callback, dataType) {
			if (url != undefined && url != null) {
				if (url.indexOf("?") == -1) {
					url = url + "?1=1";
				} else {
					url = url + "&1=1";
				}
			}
		
			try {
				var mydate = new Date();
				url = url + "&uniqueTimestamp=" + mydate.getFullYear()
						+ mydate.getMonth() + mydate.getDay() + mydate.getHours()
						+ mydate.getSeconds() + mydate.getUTCMilliseconds();
			} catch (e) {
			}
		
			// url= url + "&mediUser="+ mediUser+"&mediPass="+mediEncryptedPassword;
		
			// $('#processing-form').dialog('open');
			if (callback) {
				$.post(url, parameters, function(data) {
					// $('#processing-form').dialog('close');
					eval(callback(data));
				});
			} else {
				$.post(url, parameters, defaultCallback);
			}
		}
		
      	jQuery(document).ready(function() {
	        getKeys();
	       
	        $("#login_button").click(function() {
				onLoginButtonClicked();
			});
        });
      </script>

    </head>
    <body>
      <h2>
        Javascript-Java RSA 512 Encryption example!
      </h2>
      <fieldset>
        <label>
          Username:
        </label>
        <input type="text" id="login_user" value="">
        <label>
          Password:
        </label>
        <input type="password" id="login_password" value="">
        <input type="button" id="login_button" value="login">
      </fieldset>
    </body>

    <span id="login_status">
    </span>
  </html>
