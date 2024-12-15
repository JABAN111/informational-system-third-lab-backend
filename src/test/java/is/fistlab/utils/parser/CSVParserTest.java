package is.fistlab.utils.parser;

import org.junit.jupiter.api.Test;

import java.io.File;

class CSVParserTest {
  @Test
  public void simpleTest(){
      var pathToFile = "/Users/jaba/Documents/life/ITMO/IS/secondLab/FistLab_Backend/generated_data.csv";
      File file = new File(pathToFile);

      CSVParser csvParserImpl = new CSVParser();
      csvParserImpl.getStudyGroupsFromFile(file);

  }
    @Test
    public void idiotTest(){
        String str = "1,21";
        Long res = Long.parseLong(str);
        System.out.println(res);
    }
}