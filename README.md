# Data visualisation

## Description

Render your data into diagram

# _User guide_

## Run

* You can run this utility with this command:

```
./gradlew -q run --args="[DIAGRAM TYPE] [DIAGRAM DATA PATH] [OPTIONS]"
```

## Options

* Diagram types:
    * `-c`, `--cycle`  cycle diagram
    * `-h`, `--histogram` histogram

* Other options:
    * `-p [FILENAME]`, `--png [FILENAME]` save diagram to png file
    * `--columns [NUMBER]` set number of histogram drawing columns equal to `NUMBER`. **Note:** you can't run histogram
      without this option

## Diagram data format

* **Cycle diagram.** Each line contains number and description separated by `~`
  * Example (file `src/test/resources/IntAndStringData.txt`)
      ```
      100~Men
      100~Women
      150~Kids
      79~Animals
      ```
* **Histogram.** Each line contains single number
  * Example (file `src/test/resources/Int1To100Data.txt`)
    ```
    19
    44
    79
    ...
    ...
    25
    23
    ```
    
## Diagram image format
* **Cycle Diagram.** Show cycle divided on pies. Pie sizes show proportions of given numbers.
* **Histogram.** Divide all given numbers into ranges and show for each range how many numbers are in that range. Numbers under histogram show you minimal and maximal values in data. Number in left upper conner show maximal number of elements in range.

## Run examples

* Show cycle diagram

```
./gradlew -q run --args="-c src/test/resources/IntAndStringData.txt"
```

* Save cycle diagram to png

```
./gradlew -q run --args="-c src/test/resources/IntAndStringData.txt --png cycleDiagram.png"
```

* Show histogram

```
./gradlew -q run --args="-h src/test/resources/EvenIntsData.txt --columns 6"
```

* Save histogram to png

```
./gradlew -q run --args="-h src/test/resources/Int1To100Data.txt --columns 50 -p histogram.png"
```
