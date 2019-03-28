package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.handlers.UsersPostgresHandler;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.gitrekt.quora.models.User;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * This command is responsible for signing up the user. It does so by validating the given
 * information (checking the email is valid, password and confirm passwords match...), Afterwards it
 * hashes the password using BCrypt with `log_round = 12` and inserts the data to the SQL DB.
 */
public class SignUpCommand extends Command {

  private static final String[] argumentNames =
      new String[] {"username", "email", "password", "confirmPassword", "firstName", "lastName"};

  public SignUpCommand(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public String execute() throws SQLException, BadRequestException {
    checkArguments(argumentNames);

    String email = (String) args.get("email");
    String password = (String) args.get("password");
    String confirmPassword = (String) args.get("confirmPassword");

    validate(email, password, confirmPassword);

    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
    String username = (String) args.get("username");
    String firstName = (String) args.get("firstName");
    String lastName = (String) args.get("lastName");

    postgresHandler.insertUser(
        UUID.randomUUID(), email, username, hashedPassword, firstName, lastName);

    return "SignUp Successful !";
  }

  private void validate(String email, String password, String confirmPassword)
      throws BadRequestException {
    StringBuilder stringBuilder = new StringBuilder();
    if (!EmailValidator.getInstance().isValid(email)) {
      stringBuilder.append("Invalid Email").append("\n");
    }
    if (!password.equals(confirmPassword)) {
      stringBuilder.append("'Password' and 'Confirm Password' do not match").append("\n");
    }
    if (stringBuilder.length() > 0) {
      throw new BadRequestException(stringBuilder.toString());
    }
  }
}
