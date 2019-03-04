# XML Substitutor
[Picture](https://github.com/Indi-Code/XML-Substitutor/blob/master/example.PNG)
## Easy to run program to substitute stuff in your XML/HTML/ETC..
### Why would I want to use this?
An example is putting a header on every page on a website.
### OK, Now how do I use it?
(Coppied directly from the prohram's help button)
Place the directory you wish to use in the directory field. Inside this directory there should be a 'content' folder, along with any others you define. Other folders can be used for resources that aren't already part of your content(EX: website header). Outpus are built at 'directory/result'.
<hr/>
The XML tag is the tag used to signify a substitution. Usage is '<tag [(to keep the root tag) stays_after_sub="true"]src="resources/my_cool_xml_insert.xml"/>'
### GIMME an example!
Adding a header to your web page:

`parent/resources/header.html` (this is what you are attatching to your page)
```
<div stays_after_sub="true" <!-- Means the div element is kept, and its children are not put directly into the result page --> style="background-color:grey" <!-- All other tags stay -->>
  <ul>
    <li><a href="mycoolsite.com">Home</a><li>
    <li><a href="mycoolsite.com/whythissiteisawesome.html">Why We're Awesome
  </ul>
</div>
```
`parent/content/index.html` (the homepage)
```
<html>
    <body>
        <substitute src="resources/header.html"/>
        <h1>PRETEND THIS IS A COOL WEBSITE'S HOMEPAGE!!!</h1>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sodales, quam ultrices venenatis accumsan, eros erat viverra mauris, ac rhoncus magna arcu et dui. Nam at efficitur ex, at vehicula odio. Pellentesque euismod euismod vehicula. Cras non rhoncus nisi, in venenatis metus. Quisque at urna ultrices, tempus lectus sed, feugiat mauris. Quisque ultricies urna nec ipsum sollicitudin, at facilisis neque tempus. Suspendisse sed metus velit. Phasellus velit nulla, consectetur et mattis et, vehicula in lacus. Mauris eros dui, varius a semper et, pharetra quis purus. Curabitur porta, magna vitae mattis viverra, odio augue tincidunt sapien, et aliquam eros lorem in diam. In quis turpis nec quam ornare lobortis ac nec risus.</p>
        <p>Morbi sit amet nisi eleifend risus laoreet convallis. Integer efficitur porttitor turpis sed interdum. Fusce tincidunt sapien tincidunt ultrices sagittis. Quisque tincidunt porta nunc nec vestibulum. Mauris commodo fermentum nunc quis volutpat. Mauris egestas laoreet tellus, a tincidunt nisl interdum a. Pellentesque volutpat elit nec dolor rhoncus, non dapibus dui facilisis. In commodo neque a odio pretium lobortis. Maecenas vel elit non lacus commodo viverra non sit amet mi.</p>
    </body>
</html>
```
`parent/content/whythissiteisawesome.html` (the braggy about page)
```
<html>
    <body>
        <substitute src="resources/header.html"/>
        <h1>PRETEND THIS IS A COOL WEBSITE'S ABOUT PAGE!!!</h1>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sodales, quam ultrices venenatis accumsan, eros erat viverra mauris, ac rhoncus magna arcu et dui. Nam at efficitur ex, at vehicula odio. Pellentesque euismod euismod vehicula. Cras non rhoncus nisi, in venenatis metus. Quisque at urna ultrices, tempus lectus sed, feugiat mauris. Quisque ultricies urna nec ipsum sollicitudin, at facilisis neque tempus. Suspendisse sed metus velit. Phasellus velit nulla, consectetur et mattis et, vehicula in lacus. Mauris eros dui, varius a semper et, pharetra quis purus. Curabitur porta, magna vitae mattis viverra, odio augue tincidunt sapien, et aliquam eros lorem in diam. In quis turpis nec quam ornare lobortis ac nec risus.</p>
        <p>Morbi sit amet nisi eleifend risus laoreet convallis. Integer efficitur porttitor turpis sed interdum. Fusce tincidunt sapien tincidunt ultrices sagittis. Quisque tincidunt porta nunc nec vestibulum. Mauris commodo fermentum nunc quis volutpat. Mauris egestas laoreet tellus, a tincidunt nisl interdum a. Pellentesque volutpat elit nec dolor rhoncus, non dapibus dui facilisis. In commodo neque a odio pretium lobortis. Maecenas vel elit non lacus commodo viverra non sit amet mi.</p>
    </body>
</html>
```
When you run the program with the parent directory specified, a `parent/build` folder will be generated with everything from `parent/content` inside. The only difference is that `substitute` tags will be replaced with the specified file's content.
Examples:
`parent/build/index.html`
```
<html>
    <body>
        <div style="background-color:grey">
        <ul>
            <li>
                <a href="mycoolsite.com">Home</a>
            </li>
            <li>
                <a href="mycoolsite.com/whythissiteisawesome.html">Why We're Awesome</a>
            </li>
        </ul>
        </div> 
        <h1>PRETEND THIS IS A COOL WEBSITE'S HOMEPAGE!!!</h1>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sodales, quam ultrices venenatis accumsan, eros erat viverra mauris, ac rhoncus magna arcu et dui. Nam at efficitur ex, at vehicula odio. Pellentesque euismod euismod vehicula. Cras non rhoncus nisi, in venenatis metus. Quisque at urna ultrices, tempus lectus sed, feugiat mauris. Quisque ultricies urna nec ipsum sollicitudin, at facilisis neque tempus. Suspendisse sed metus velit. Phasellus velit nulla, consectetur et mattis et, vehicula in lacus. Mauris eros dui, varius a semper et, pharetra quis purus. Curabitur porta, magna vitae mattis viverra, odio augue tincidunt sapien, et aliquam eros lorem in diam. In quis turpis nec quam ornare lobortis ac nec risus.</p>
        <p>Morbi sit amet nisi eleifend risus laoreet convallis. Integer efficitur porttitor turpis sed interdum. Fusce tincidunt sapien tincidunt ultrices sagittis. Quisque tincidunt porta nunc nec vestibulum. Mauris commodo fermentum nunc quis volutpat. Mauris egestas laoreet tellus, a tincidunt nisl interdum a. Pellentesque volutpat elit nec dolor rhoncus, non dapibus dui facilisis. In commodo neque a odio pretium lobortis. Maecenas vel elit non lacus commodo viverra non sit amet mi.</p>
    </body>
</html>
```
and you get the picture. If you try the html, you can see how the header is put in seamlessly.
[Download Here](https://github.com/Indi-Code/XML-Substitutor/releases)
