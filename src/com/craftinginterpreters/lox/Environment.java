package com.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

class Environment {
  final Environment enclosing;
  private final Map<String, Object> values = new HashMap<>();
  Environment() {
    enclosing = null;
  }
  
	//Add this method to your Environment class
	void assignGlobal(String name, Object value) {
	   if (enclosing != null) {
	       enclosing.assignGlobal(name, value);
	   } else {
	       values.put(name, value);
	   }
	}

  Environment(Environment enclosing) {
    this.enclosing = enclosing;
  }

  Object get(Token name) {
    if (values.containsKey(name.lexeme)) {
      return values.get(name.lexeme);
    }

    if (enclosing != null) return enclosing.get(name);

    throw new RuntimeError(name,
        "Undefined variable '" + name.lexeme + "'.");
  }

  void assign(Token name, Object value) {
    if (values.containsKey(name.lexeme)) {
      values.put(name.lexeme, value);
      return;
    }

    if (enclosing != null) {
      enclosing.assign(name, value);
      return;
    }

    throw new RuntimeError(name,
        "Undefined variable '" + name.lexeme + "'.");
  }
  void define(String name, Object value) {
    values.put(name, value);
  }
//  @Override
//  public String toString() {
//    String result = values.toString();
//    if (enclosing != null) {
//      result += " -> " + enclosing.toString();
//    }
//
//    return result;
//  }
}
