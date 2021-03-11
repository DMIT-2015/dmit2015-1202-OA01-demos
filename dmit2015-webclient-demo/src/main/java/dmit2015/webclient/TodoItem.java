package dmit2015.webclient;

import lombok.Data;

@Data
public class TodoItem {

    private Long id;

    private String name;

    private boolean complete;

    private Integer version;

}

