### stream을 활용한 file 제어

1. 모든 리소스(resource)는 try-with-resources 방식을 사용하여 읽는다.
2. Files를 활용하여 File / Directory 접근을 한다.

#### 1. 특정 파일 내 필터링

>Path path = Paths.get(ClassLoader.getSystemResource("book_info.txt").toURI());
> try (BufferedReader br = Files.newBufferedReader(path)) {...}
> Stream<String> lines = br.lines();

특정 파일을 try-with-resources를 활용하여 읽는다.<br>
InputStream을 통해 Stream을 생성한다.

> long startsWithA = lines.filter(line -> line.startsWith("A")).count();

해당 파일 내 "A"로 시작하는 문장의 개수를 판별한다.

>Set<String> words = lines.flatMap(line ->
>Stream.of(line.split("\\W+")))
>.collect(Collectors.toSet());

flatMap() 을 활용하여 문장을 내 단어를 추출한다.
* 위 정규식 경우 영어만 판별한다.

<br>

#### 2. CSV 파일 읽기
CSV는 ,를 구분자로 두기 때문에 Stream을 적절히 활용하면,
CSV 파일 읽기도 수월해진다.

    #Cakes
    1, Pound Cake,100
    2, Red Velvet Cake,500
    3, Carrot Cake,300
    4, Sponge Cake,400
    5, Chiffon Cake,600

위와 같이 케이크의 정보를 담은 CSV 파일이 있다고 가정하자.

    public class Cake {
        private int id;
        private String name;
        private int price;
        ... 

정보를 토대로 객체로 구현한다.

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

파읽 읽기를 통해 Stream을 생성 후 , 구분으로 split를 진행하면 수월하게 자바 객체로의 변환이 가능하다.