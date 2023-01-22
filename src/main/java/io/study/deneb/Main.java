package io.study.deneb;

import io.study.deneb.model.Cake;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) throws IOException, URISyntaxException {
    Path path = Paths.get(ClassLoader.getSystemResource("book_info.txt").toURI());

    try (BufferedReader br = Files.newBufferedReader(path)) {
      Stream<String> lines = br.lines();

      long startsWithA = lines.filter(line -> line.startsWith("A")).count();

      //System.out.println(startsWithA); // 2

      Set<String> words = lines.flatMap(line ->
          Stream.of(line.split("\\W+")))
        .collect(Collectors.toSet());

      System.out.println(words);
    }

    // CSV
    Path csvPath = Paths.get(ClassLoader.getSystemResource("cakes.csv").toURI());
    try (BufferedReader br = Files.newBufferedReader(csvPath)) {
      Stream<String> csvLines = br.lines();
      List<Cake> cakes = csvLines.skip(1)
        .map(line -> {
          String[] arr = line.split(",");
          return new Cake(
            Integer.parseInt(arr[0]),
            arr[1],
            Integer.parseInt(arr[2]));
        })
        .collect(Collectors.toList());
    }


    // Listing Directory
    Path folderPath = Paths.get(ClassLoader.getSystemResource("cook").toURI());
    try (Stream<Path> stream = Files.walk(folderPath)) {
//      stream.filter(Files::isRegularFile)
//        .forEach(System.out::println);

      List<String> chineseCookList = stream.filter(book -> book.getFileName().toString().contains("chinese"))
        .map(book -> book.getFileName().toString())
        .toList();

    }

    // Finding File
    Path searchPath = Paths.get(ClassLoader.getSystemResource("").toURI());
    try (Stream<Path> stream = Files.find(searchPath,
      Integer.MAX_VALUE,
      (p, attr) -> attr.isRegularFile() && p.getFileName().toString().contains(".pdf"))) {
      stream.forEach(System.out::println);
    }





  }

}
