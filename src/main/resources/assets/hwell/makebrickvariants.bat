SET name=%1_brick

(
@echo { "variants": {														 
@echo     "facing=north": { "model": "hwell:%name%_slab_side", "y": 180 },
@echo     "facing=south": { "model": "hwell:%name%_slab_side" },
@echo     "facing=west":  { "model": "hwell:%name%_slab_side", "y": 90 },
@echo     "facing=east":  { "model": "hwell:%name%_slab_side", "y": 270 },
@echo     "facing=up":  { "model": "hwell:%name%_slab" },
@echo     "facing=down":  { "model": "hwell:%name%_slab", "x": 180 }
@echo } }
)>blockstates/%name%_slab.json


(
@echo {
@echo     "parent": "hwell:block/slab_base",
@echo     "textures": {
@echo         "particle": "hwell:blocks/%name%",
@echo         "0": "hwell:blocks/%name%",
@echo         "1": "hwell:blocks/%name%"
@echo     }
@echo }
)>models/block/%name%_slab.json


(
@echo {
@echo     "parent": "hwell:block/slab_base_side",
@echo     "textures": {
@echo         "particle": "hwell:blocks/%name%",
@echo         "0": "hwell:blocks/%name%",
@echo         "1": "hwell:blocks/%name%"
@echo     }
@echo }
)>models/block/%name%_slab_side.json


(
@echo {
@echo     "parent": "hwell:block/%name%_slab"
@echo }
)>models/item/%name%_slab.json

(
@echo { "type": "minecraft:crafting_shaped",
@echo   "pattern": ["AAA"],
@echo   "key": {"A": [{"item": "hwell:%name%"}]},
@echo   "result": { "item": "hwell:%name%_slab", "count": 6 }}
)>recipes/%name%_slab.json


(
@echo { "type": "minecraft:crafting_shaped",
@echo   "pattern": ["A","A"],
@echo   "key": {"A": [{"item": "hwell:%name%_slab"}]},
@echo   "result": { "item": "hwell:%name%" }}
)>recipes/%name%_unslab.json







(
@echo { "variants": {
@echo         "facing=east,half=bottom,shape=straight":  { "model": "hwell:%name%_stairs" },
@echo         "facing=west,half=bottom,shape=straight":  { "model": "hwell:%name%_stairs", "y": 180, "uvlock": true },
@echo         "facing=south,half=bottom,shape=straight": { "model": "hwell:%name%_stairs", "y": 90, "uvlock": true },
@echo         "facing=north,half=bottom,shape=straight": { "model": "hwell:%name%_stairs", "y": 270, "uvlock": true },
@echo         "facing=east,half=bottom,shape=outer_right":  { "model": "hwell:%name%_outer_stairs" },
@echo         "facing=west,half=bottom,shape=outer_right":  { "model": "hwell:%name%_outer_stairs", "y": 180, "uvlock": true },
@echo         "facing=south,half=bottom,shape=outer_right": { "model": "hwell:%name%_outer_stairs", "y": 90, "uvlock": true },
@echo         "facing=north,half=bottom,shape=outer_right": { "model": "hwell:%name%_outer_stairs", "y": 270, "uvlock": true },
@echo         "facing=east,half=bottom,shape=outer_left":  { "model": "hwell:%name%_outer_stairs", "y": 270, "uvlock": true },
@echo         "facing=west,half=bottom,shape=outer_left":  { "model": "hwell:%name%_outer_stairs", "y": 90, "uvlock": true },
@echo         "facing=south,half=bottom,shape=outer_left": { "model": "hwell:%name%_outer_stairs" },
@echo         "facing=north,half=bottom,shape=outer_left": { "model": "hwell:%name%_outer_stairs", "y": 180, "uvlock": true },
@echo         "facing=east,half=bottom,shape=inner_right":  { "model": "hwell:%name%_inner_stairs" },
@echo         "facing=west,half=bottom,shape=inner_right":  { "model": "hwell:%name%_inner_stairs", "y": 180, "uvlock": true },
@echo         "facing=south,half=bottom,shape=inner_right": { "model": "hwell:%name%_inner_stairs", "y": 90, "uvlock": true },
@echo         "facing=north,half=bottom,shape=inner_right": { "model": "hwell:%name%_inner_stairs", "y": 270, "uvlock": true },
@echo         "facing=east,half=bottom,shape=inner_left":  { "model": "hwell:%name%_inner_stairs", "y": 270, "uvlock": true },
@echo         "facing=west,half=bottom,shape=inner_left":  { "model": "hwell:%name%_inner_stairs", "y": 90, "uvlock": true },
@echo         "facing=south,half=bottom,shape=inner_left": { "model": "hwell:%name%_inner_stairs" },
@echo         "facing=north,half=bottom,shape=inner_left": { "model": "hwell:%name%_inner_stairs", "y": 180, "uvlock": true },
@echo         "facing=east,half=top,shape=straight":  { "model": "hwell:%name%_stairs", "x": 180, "uvlock": true },
@echo         "facing=west,half=top,shape=straight":  { "model": "hwell:%name%_stairs", "x": 180, "y": 180, "uvlock": true },
@echo         "facing=south,half=top,shape=straight": { "model": "hwell:%name%_stairs", "x": 180, "y": 90, "uvlock": true },
@echo         "facing=north,half=top,shape=straight": { "model": "hwell:%name%_stairs", "x": 180, "y": 270, "uvlock": true },
@echo         "facing=east,half=top,shape=outer_right":  { "model": "hwell:%name%_outer_stairs", "x": 180, "y": 90, "uvlock": true },
@echo         "facing=west,half=top,shape=outer_right":  { "model": "hwell:%name%_outer_stairs", "x": 180, "y": 270, "uvlock": true },
@echo         "facing=south,half=top,shape=outer_right": { "model": "hwell:%name%_outer_stairs", "x": 180, "y": 180, "uvlock": true },
@echo         "facing=north,half=top,shape=outer_right": { "model": "hwell:%name%_outer_stairs", "x": 180, "uvlock": true },
@echo         "facing=east,half=top,shape=outer_left":  { "model": "hwell:%name%_outer_stairs", "x": 180, "uvlock": true },
@echo         "facing=west,half=top,shape=outer_left":  { "model": "hwell:%name%_outer_stairs", "x": 180, "y": 180, "uvlock": true },
@echo         "facing=south,half=top,shape=outer_left": { "model": "hwell:%name%_outer_stairs", "x": 180, "y": 90, "uvlock": true },
@echo         "facing=north,half=top,shape=outer_left": { "model": "hwell:%name%_outer_stairs", "x": 180, "y": 270, "uvlock": true },
@echo         "facing=east,half=top,shape=inner_right":  { "model": "hwell:%name%_inner_stairs", "x": 180, "y": 90, "uvlock": true },
@echo         "facing=west,half=top,shape=inner_right":  { "model": "hwell:%name%_inner_stairs", "x": 180, "y": 270, "uvlock": true },
@echo         "facing=south,half=top,shape=inner_right": { "model": "hwell:%name%_inner_stairs", "x": 180, "y": 180, "uvlock": true },
@echo         "facing=north,half=top,shape=inner_right": { "model": "hwell:%name%_inner_stairs", "x": 180, "uvlock": true },
@echo         "facing=east,half=top,shape=inner_left":  { "model": "hwell:%name%_inner_stairs", "x": 180, "uvlock": true },
@echo         "facing=west,half=top,shape=inner_left":  { "model": "hwell:%name%_inner_stairs", "x": 180, "y": 180, "uvlock": true },
@echo         "facing=south,half=top,shape=inner_left": { "model": "hwell:%name%_inner_stairs", "x": 180, "y": 90, "uvlock": true },
@echo         "facing=north,half=top,shape=inner_left": { "model": "hwell:%name%_inner_stairs", "x": 180, "y": 270, "uvlock": true }
@echo } }
)>blockstates/%name%_stairs.json

(
@echo {
@echo     "parent": "hwell:block/%name%_stairs"
@echo }
)>models/item/%name%_stairs.json

(
@echo {
@echo     "parent": "block/stairs",
@echo     "textures": { "bottom": "hwell:blocks/%name%", "top": "hwell:blocks/%name%", "side": "hwell:blocks/%name%" }
@echo }
)>models/block/%name%_stairs.json
(
@echo {
@echo     "parent": "block/outer_stairs",
@echo     "textures": { "bottom": "hwell:blocks/%name%", "top": "hwell:blocks/%name%", "side": "hwell:blocks/%name%" }
@echo }
)>models/block/%name%_outer_stairs.json
(
@echo {
@echo     "parent": "block/inner_stairs",
@echo     "textures": { "bottom": "hwell:blocks/%name%", "top": "hwell:blocks/%name%", "side": "hwell:blocks/%name%" }
@echo }
)>models/block/%name%_inner_stairs.json

(
@echo { "type": "minecraft:crafting_shaped",
@echo   "pattern": [ "AAA" ],
@echo   "key": { "A": [{"item": "hwell:%name%"}] },
@echo   "result": { "item": "hwell:%name%_slab", "count": 6 }}
)>recipes/%name%_slab.json


(
@echo { "type": "minecraft:crafting_shaped",
@echo   "pattern": ["AA","AA"],
@echo   "key": {"A": [{"item": "hwell:%name%_stairs"}]},
@echo   "result": { "item": "hwell:%name%", "count": 3 }}
)>recipes/%name%_unstairs.json

(
@echo { "type": "minecraft:crafting_shaped",
@echo   "pattern": [ "A ","AA" ],
@echo   "key": {"A": [{"item": "hwell:%name%"}]},
@echo   "result": { "item": "hwell:%name%_stairs", "count": 4 }}
)>recipes/%name%_stairs.json