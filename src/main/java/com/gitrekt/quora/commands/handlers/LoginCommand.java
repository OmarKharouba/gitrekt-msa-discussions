package com.gitrekt.quora.commands.handlers;

import com.gitrekt.quora.authentication.Jwt;
import com.gitrekt.quora.commands.Command;
import com.gitrekt.quora.database.postgres.handlers.UsersPostgresHandler;
import com.gitrekt.quora.exceptions.AuthenticationException;
import com.gitrekt.quora.exceptions.BadRequestException;
import com.gitrekt.quora.models.User;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * This command is responsible for login. It does so by getting the user by the provided email from
 * the database, and checking his hashed password against the provided one. It throws an exception
 * if no user with the corresponding email exists or the password is incorrect, otherwise it returns
 * a JWT.
 */
public class LoginCommand extends Command {

  private static final String[] argumentNames = new String[] {"email", "password"};

  public LoginCommand(HashMap<String, Object> args) {
    super(args);
  }

  @Override
  public String execute() throws SQLException, BadRequestException, AuthenticationException {
    checkArguments(argumentNames);
    String email = (String) args.get("email");
    String password = (String) args.get("password");

    User user = postgresHandler.getUserPassword(email);
    if (user == null) {
      throw new BadRequestException("No account with this email was found");
    }
    if (user.getHashedPassword() != null && BCrypt.checkpw(password, user.getHashedPassword())) {
      Map<String, String> claims = new HashMap<>();
      claims.put("userId", user.getUserId());
      //      claims.put("scope", "user");
      return Jwt.generateToken(claims, 60);
    }
    throw new AuthenticationException("Incorrect Password");
  }
}
