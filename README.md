# Shifting-Colors
Creates Images of Selfsimilar Tilings with shifting colors that can be put together to a video.
This is accomplished by continuously changing the color instruction for the iteration of the Tiling.

To get started you need to make a java project containing all these classes. (I use eclipse).

ShiftingColor contains the main method.
Things that can be changed:
line 15: The rgb values of the list of colors to cycle through
line 20: The set of prototiles, the tiling is built from. Different sets can be found in the methods of the class Tesselate
line 50: The iteration instruction, must mach the prototiles. They are also in the class Tesselate
line 9: the lengths must match with the lengths in the chosen instruction.
line 42: these are the initial&second color instructions. Number of digits must be the sum of the lengths from above, the digits must be smaller than the number of colors.
          only one digit changes by +1 between the two
line 44: which digit is changed 

line 22: steps specifies into how many steps the path between adjacent colours is divided.
line 24: specifies what region of the plane is depicted, to get a full image, it all has to lie within the prototile
line 26: The name of the generated files 


If you have questions, feel free to ask, e.g. in my discord: https://discord.gg/3XNxnGZRVs

You can support me on Patreon: https://www.patreon.com/noneuclideandreamer
