README on "Non-unit Tests"

/src/test/nonunittests/lilypond

  Contains .xml files and their expected output in .ly format.
  
  Testing steps:
  
  1)  Launch Lilypond.php (located in /src/main/webapp/)
      with the 'testfile' GET attribute set to the xml test file
      (relative to the Lilypond's path).
      
      e.g. Lilypond.php?testfile=rests.xml
  
  2)  Lilypond.php should output the name of the generated file as HTML content.
      Lilypond.php should generate a .ly file with that name in songs/
      (same directory as Lilypond.php).
      
  3)  Compare the generated .ly file with the expected output.
      
      e.g. rests.ly