package com.yuri.taskmanager.application.input;

import java.util.Optional;

public record CreateTaskInput(String name, Optional<String> description) {

}
