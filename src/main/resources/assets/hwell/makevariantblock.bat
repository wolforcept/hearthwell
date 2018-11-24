SET name=%1

@echo {>blockstates/%name%.json
@echo     "variants": {>>blockstates/%name%.json
@echo         "normal": [>>blockstates/%name%.json
@echo             { "model": "hwell:%name%" }>>blockstates/%name%.json
@echo         ]>>blockstates/%name%.json
@echo     }>>blockstates/%name%.json
@echo }>>blockstates/%name%.json

@echo {>models/block/%name%.json
@echo     "parent": "block/cube_all",>>models/block/%name%.json
@echo     "textures": {>>models/block/%name%.json
@echo         "all": "hwell:blocks/%name%">>models/block/%name%.json
@echo     }>>models/block/%name%.json
@echo }>>models/block/%name%.json

@echo {>models/item/%name%.json
@echo     "parent": "hwell:block/%name%">>models/item/%name%.json
@echo }>>models/item/%name%.json


(
@echo { "type": "minecraft:crafting_shaped",
@echo   "pattern": ["AA","AA"],
@echo   "key": {"A": [{"item": "hwell:%name%"}]},
@echo   "result": { "item": "hwell:%name%_bricks", "count": 4 }}
)>recipes/%name%_bricks.json

(
@echo { "type": "minecraft:crafting_shaped",
@echo   "pattern": ["AA","AA"],
@echo   "key": {"A": [{"item": "hwell:%name%_bricks"}]},
@echo   "result": { "item": "hwell:%name%", "count": 4 }}
)>recipes/%name%_unbricks.json
