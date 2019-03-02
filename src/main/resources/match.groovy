@Grab('org.apache.commons:commons-lang3:3.5')
import static org.apache.commons.lang3.StringUtils.getJaroWinklerDistance

def result = ""

// Check inputs
if(text == null || text.trim().isEmpty() || keywords == null || keywords.trim().isEmpty()){
  result = "No match"
} else {
  def words = keywords.toLowerCase().split(',')
  def textList = text.toLowerCase().split().toList().unique()
  def total = 0.0
  
  words.each { word ->
    total += textList.collect { n->
        new Tuple(n, getJaroWinklerDistance(n, word))
    }.sort { -it.get(1) }.first().get(1)
  }

  result = total / words.size() > accuracy ? "Match" : "No match"
}

payload = "{ \"result\": \"" + result + "\" }"