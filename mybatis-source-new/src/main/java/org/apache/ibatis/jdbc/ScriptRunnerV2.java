package org.apache.ibatis.jdbc;

import lombok.Data;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.*;
import java.util.stream.Collectors;

/**
 * @author xiehongwei
 * @date 2021/12/9 3:27 PM
 */
@Data
public class ScriptRunnerV2 {
    private Connection connection;
    private Boolean stopOnError;
    private Boolean autoCommit;
    private Boolean sendFullScript;
    private Boolean removeCRS;
    private Boolean escapeProcessing = true;

    private PrintWriter logWriter = new PrintWriter(System.out);
    private PrintWriter errorLogWriter = new PrintWriter(System.err);

    private String delimiter = ";";
    private Boolean fulllineDelimiter = false;

    public ScriptRunnerV2(Connection connection) {
        this.connection = connection;
    }

    @SneakyThrows
    public void runScript(Reader reader) {
        connection.setAutoCommit(autoCommit);
        try {
            if (sendFullScript) {
                exeFull(reader);
            } else {
                exeLine(reader);
            }
        } finally {
            rollback();
        }
    }

    @SneakyThrows
    private void rollback() {
        if (!connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    @SneakyThrows
    private void exeLine(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder command = new StringBuilder();
        bufferedReader.lines().forEach(line->{
            handleLine(command,line);
        });
        if (!connection.getAutoCommit()) {
            connection.commit();
        }
    }

    private StringBuilder handleLine(StringBuilder command, String line) {
        line = line.trim();
        if (line.startsWith("//") || line.startsWith("--")) {
            logWriter.println(line);
        }else {
            command.append(line)
                    .append(System.lineSeparator());
        }
        return command;
    }

    @SneakyThrows
    private void exeFull(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String script = bufferedReader.lines().collect(Collectors.joining(";"));
        exeStatement(script);
        if (!connection.getAutoCommit()) {
            connection.commit();
        }
    }

    @SneakyThrows
    private void exeStatement(String sql) {
        boolean hasResult = false;
        Statement statement = connection.createStatement();
        statement.setEscapeProcessing(escapeProcessing);
        if (removeCRS) {
            sql = sql.replaceAll("\r\n", "\n");
        }
        if (stopOnError) {
            hasResult = statement.execute(sql);
        } else {
            try {
                hasResult = statement.execute(sql);
            } catch (SQLException e) {
            }
        }
        printResult(statement, hasResult);
        statement.close();
    }

    @SneakyThrows
    private void printResult(Statement statement, boolean hasResult) {
        if (hasResult) {
            ResultSet rs = statement.getResultSet();
            if (rs == null) {
                return;
            }
            ResultSetMetaData metaData = rs.getMetaData();
            int cols = metaData.getColumnCount();
            for (int i = 1; i <= cols; i++) {
                String name = metaData.getColumnLabel(i);
                logWriter.print(name+"\t");
                logWriter.flush();
            }
            logWriter.println();
            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    String value = rs.getString(i);
                    logWriter.print(value + "\t");
                    logWriter.flush();
                }
                logWriter.println();
            }
        }
    }

}
