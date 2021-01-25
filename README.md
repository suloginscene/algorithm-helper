# 알고리즘 헬퍼

알고리즘 학습을 위한 자바 프레임워크입니다.   
정렬, 이진탐색트리, 그래프, 부호화를 학습할 수 있습니다.

## 소개

트리를 공부하면서 구현보다 확인에 더 큰 어려움을 겪어보신 적 없으신가요?  
인코딩을 구현하면서, 디코딩 전까지는 제대로 되어가는지 알 수 없어 답답하지는 않으셨나요?  
기껏 구현하고 보니 쓸모없는 인터페이스였다거나, 설계를 고민하느라 알고리즘에 집중하지 못하신 적은 없으십니까?

저는 고생했습니다.   
다행히도 시간 여유가 있었기에 결국 다방면에서의 소중한 경험이 되었지만,  
자료구조와 알고리즘을 효율적으로 학습할 수 있는 도구가 있으면 좋겠다고 생각했습니다.

알고리즘 헬퍼는   
구현해야 할 인터페이스, 구현과 확인에 활용되는 기본 메서드를 제공하며,   
구현이 올바른지를 검증하고, 수행시간을 측정하며, 테스트 데이터를 생성합니다.

### 훑어보기

```java
// 알고리즘 헬퍼의 인터페이스(추상클래스)를 구현하세요. (상속 직후의 초기 상태입니다.)
public class AvlTree extends BinarySearchTree<Integer, String> {
    @Override
    protected Node<Integer, String> getRoot() {
        return null;
    }

    @Override
    protected void setRoot(Node<Integer, String> node) {
    }

    @Override
    protected void doSave(@NonNull Node<Integer, String> node) {
    }

    @Override
    public Optional<Node<Integer, String>> findNode(@NonNull Integer key) {
        return Optional.empty();
    }

    @Override
    protected void doDelete(@NonNull Integer key) {
    }

    @Override
    public void inOrder(Consumer<Node<Integer, String>> consumer) {
    }
}
```

```java
// 구현하실 추상메서드의 바깥에서는 입력, 상태, 구현 등을 검증합니다.
public abstract class BinarySearchTree<K extends Comparable<K>, V> {
    // 템플릿메서드
    public void save(@NonNull K key, @NonNull V value) {
        ValidateUtil.validateKey(getRoot(), key); // 키 중복 검증
        doSave(new Node<>(key, value)); // 추상메서드
        ValidateUtil.validateBst(getRoot()); // 정렬 검증
    }

    // 추상메서드
    protected abstract void doSave(@NonNull Node<K, V> node);
}
```

```java
// 데이터생성기, 프로파일러, 미리 구현된 출력 메서드와 함께 테스트를 꼭 작성하세요.
class AvlTreeTest {
    @Test
    void find() {
        // n에 따라 항상 동일하게 섞인 정수 리스트 생성
        Integers integers = IntegersFactory.stablyShuffled(1000, true);
        // 원하는 타입의 리스트로 변환
        List<Node<Integer, String>> nodes = integers.toTypeList(i -> new Node<>(i, Integer.toHexString(i)));

        // 프로파일러를 프록시로 사용
        BinarySearchTree<Integer, String> bst = new BinarySearchTreeProfiler<>(new AvlTree());
        // 테스트 전 데이터 입력 및 진행 상황 로깅
        BinarySearchTreeUtil.saveDataLoggingProgress(bst, nodes, 100);

        // 테스트 데이터 리스트의 중앙 값
        Integer key = integers.getMid();
        // When
        Object value = bst.findValue(key).orElse(null);
        // Then
        assertEquals(Integer.toHexString(key), value);

        // 트리 형태로 노드의 toString() 출력
        bst.print(Node::toString);
        // 루트부터 리프까지의 모든 경로 출력
        bst.printPaths();
    }
}
        /*  개략적인 로그
            > Integers created in 1 ms.
            > [8, 14, 7, 13, 6, 12, 5, 11, 4, 10, 2, 3, 9, 1]
            
            data 0 saved.
            data 5 saved.
            data 10 saved.
            > BST Initialized in 8 ms.
            
            > Node 11 found in 0 ms.
              Path: 8 - 11
            
            > Print Tree
                                 ( 8:8)                     
                     ( 4:4)                  (11:b)         
               ( 2:2)      ( 6:6)      (10:a)      (13:d)   
            ( 1:1)( 3:3)( 5:5)( 7:7)( 9:9)(    )(12:c)(14:e)
            
            > Print paths
               8 -  4 -  2 -  1
               8 -  4 -  2 -  3
               8 -  4 -  6 -  5
               8 -  4 -  6 -  7
               8 - 11 - 10
               8 - 11 - 10 -  9
               8 - 11 - 13 - 12
               8 - 11 - 13 - 14
         */
```

## 사용방법

### 메이븐 의존성 추가

알고리즘 헬퍼는 lombok과 logback, 그리고 junit을 포함하고 있습니다.

```xml

<dependency>
    <groupId>com.github.suloginscene</groupId>
    <artifactId>algorithm-helper</artifactId>
    <version>1.0.5</version>
</dependency>
```

### 정렬

int[]를 오름차순으로 정렬합니다.

- **Sort**는 구현하셔야 하는 추상클래스입니다. 추상 메서드를 감싼 템플릿 메서드가 정렬의 성공을 검증합니다.

```java
public class SortImpl extends Sort {
    @Override
    protected void doExecute(int[] array) {
    }
}
```

- **SortContainer**는 Sorter에 주입되어 Sort들을 전달합니다. 단순히 각 Sort를 반환하도록 구현하시면 됩니다.
- **Strategy**는 단지 Sort를 선택하는 데 사용되는 enum입니다.
- **Sorter**는 Strategy에 따라 Sort를 선택하여 정렬을 수행하는 구체 클래스입니다.
- **SorterProfiler**는 정렬의 수행시간을 측정합니다. 검증을 포함한 시간을 측정하므로, 상대적인 시간을 확인해주세요.

### 이진탐색트리

제네릭한 이진탐색트리입니다.

- **BinarySearchTree**는 구현하셔야 하는 추상클래스입니다. 구체클래스의 필드에 Node root를 가져야 합니다. 기본적인 이진탐색트리와 AVL트리에는 사용하실 수 있지만, nil 노드를 지원하지
  않으므로 레드블랙트리에는 적용이 어렵다고 생각합니다. 저장 및 삭제 시 추상메서드를 감싼 템플릿 메서드가 정렬 상태를 검증합니다. 크기, 출력, 관련 노드, 회전, 옮기기에 관한 기본 메서드들을 제공합니다.

```java
public class BinarySearchTreeImpl<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {
    @Override
    protected Node<K, V> getRoot() {
        return null;
    }

    @Override
    protected void setRoot(Node<K, V> node) {
    }

    @Override
    protected void doSave(@NonNull Node<K, V> node) {
    }

    @Override
    public Optional<Node<K, V>> findNode(@NonNull K key) {
        return Optional.empty();
    }

    @Override
    protected void doDelete(@NonNull K key) {
    }

    @Override
    public void inOrder(Consumer<Node<K, V>> consumer) {
    }
}
```

- **Node**는 구체 클래스입니다. K key, V value와 left, right 자식을 갖습니다. 비교 및 자식에 관한 기본 메서드들을 제공합니다. 확장은 가능하지만, 제공되는 트리의 특성상 필요성이
  낮다고 생각합니다.
- **BinarySearchTreeProfiler**는 탐색의 수행시간을 측정하고, 탐색 경로를 기록합니다.

### 그래프

제네릭한 방향 가중치 그래프입니다.

- **Graph**는 구현하셔야 하는 추상클래스입니다. 구체클래스의 필드에 Map\<V, Set\<V\>\> vertexMap, Map\<Edge\<V\>, Edge\<V\>\> edgeMap을 가져야 합니다.
  방향성이나 가중치를 없애고자 한다면, 구현되어 있는 `addEdge(V from, V to, int weight)`를 재정의하실 수 있지만, 대부분의 경우 원 상태로도 정상동작하리라고 생각됩니다. 이를 재정의하지
  않고 무방향 그래프를 만들기 위해 양방향으로 연결하는 것은 권장하지 않습니다. 위상정렬 전 사이클이 없음을, 최단경로찾기 및 최소신장트리생성 전 연결그래프임을 검증합니다. 그래프의 상태에 관한 기본 메서드들을
  제공합니다.

```java
public class GraphImpl<V> extends Graph<V> {
    @Override
    protected Map<V, Set<V>> getVertexMap() {
        return null;
    }

    @Override
    protected Map<Edge<V>, Edge<V>> getEdgeMap() {
        return null;
    }

    @Override
    public void bfs(@NonNull V start, Consumer<V> consumer) {
    }

    @Override
    public void dfs(@NonNull V start, Consumer<V> consumer) {
    }

    @Override
    protected void doTopologicalSort(Consumer<V> consumer) {
    }

    @Override
    protected Map<V, Course<V>> doShortestCourses(@NonNull V start) {
        return null;
    }

    @Override
    protected Graph<V> doMinimumSpanningTree() {
        return null;
    }
}
```

- **Edge**는 V from, V to, int weight를 갖는 edge를 저장하기 위한 구체클래스입니다. 가중치를 기준으로 Comparable이, from과 to로 EqualsAndHashcode가
  구현되어 있습니다.
- **Course**는 OneToAll 최단경로찾기에서 반환하는 Map\<V, Course\>에 담기 위한 구체클래스입니다. List\<V\> course와 int distance를 갖습니다. 출력 메서드가
  구현되어 있습니다.
- **SetSet**은 HashSet\<Set\<V\>\>를 확장하며, 구현에 활용되는 유틸리티성 컬렉션입니다. `Set<V> findSet(V vertex)`
  와 `void union(Set<V> a, Set<V> b)`를 제공합니다.

### 부호화

문자열을 전치코드로 부호화된 문자열로 변환합니다.

- **Encoder**는 구현하셔야 하는 추상클래스입니다. 압축 자체에 관한 기능은 제공하지 않습니다. 압축 관련 학습을 원하시는 경우에는, 텍스트 문자열을 0과 1로 이루어진 이진 문자열로 변환하여 주시기
  바랍니다. `String encode(String text)`는 템플릿 메서드로서 전체 흐름을 제어합니다. 사용자가 정의한 `List<String> tokenize(String text)`
  와 `Map<String, String> mapTokenCode(List<String tokens)`의 결과를 받아, 전치코드를 검증하고, 메타데이터를 포함한 부호화된 문자열을 내보냅니다.
  `String decode(String encoded)`는 기본으로 제공됩니다. 런렝스 인코딩에 관하여는 `tokenize()`에서, 허프만 코딩에 관하여는 `mapTokenCode()`에서 작업하시면 적당하리라
  생각합니다.

```java
public class EncoderImpl extends Encoder {
    @Override
    protected List<String> tokenize(String text) {
        return null;
    }

    @Override
    protected Map<String, String> mapTokenCode(List<String> tokens) {
        return null;
    }
}
```

- **EncoderProfiler**는 부호화된 문자열의 본문이 이진 문자열인 경우, 이를 비트열이라고 생각하면 얼만큼 압축되는지를 측정합니다.

### 기타(유틸리티)

- **SortUtil**은 `void swap(int[] array, int i, int j)`를 제공합니다.
- **BinarySearchTreeUtil**은 `saveDataLogginProgress()`를 제공합니다. 자가균형 이진탐색트리는 저장이 다소 느릴 수 있는데, 대량의 테스트 데이터 입력 시 진행상황을 확인하고
  싶다면 사용할 수 있습니다.
- **Integers**는 List\<Integer\>를 감싸는 데이터 객체이며, int[] 및 List\<T\>를 추출하는 메서드를 제공합니다.
- **IntegersFactory**는 1부터 n까지의 Integers를 생성하는데, 오름차순, 무작위 셔플, 안정적 셔플(같은 n에 대하여 항상 동일한 결과를 갖는 셔플) 상태로 생성합니다.
- **Alphabet**은 단지 enum입니다. Graph\<V>에서 V(vertex)를 담당하는 등으로 활용할 수 있습니다.

## 더 보기

- [예시 코드](https://github.com/suloginscene/algorithm-helper-usage)
- [개발자 블로그](https://onpaper.cf/topics/2103)
- [알고리즘 헬퍼 Javadoc](https://suloginscene.github.io/algorithm-helper/)
