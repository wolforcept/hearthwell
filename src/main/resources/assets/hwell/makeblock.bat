SET /p name="Block Name: "
@echo off

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
