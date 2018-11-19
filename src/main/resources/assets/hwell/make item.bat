SET /p name="Block Name: "
@echo off

@echo {>models/item/%name%.json
@echo     "parent": "item/generated",>>models/item/%name%.json
@echo     "textures": {>>models/item/%name%.json
@echo         "layer0": "hwell:items/%name%">>models/item/%name%.json
@echo      },>>models/item/%name%.json
@echo     "display": {>>models/item/%name%.json
@echo         "thirdperson": {>>models/item/%name%.json
@echo             "rotation": [-90,0,0],>>models/item/%name%.json
@echo             "translation": [0,1,-3],>>models/item/%name%.json
@echo             "scale": [0.55,0.55,0.55]>>models/item/%name%.json
@echo         },>>models/item/%name%.json
@echo         "firstperson": {>>models/item/%name%.json
@echo             "rotation": [0,-135,25],>>models/item/%name%.json
@echo             "translation": [0,4,2],>>models/item/%name%.json
@echo             "scale": [1.7,1.7,1.7]>>models/item/%name%.json
@echo         }>>models/item/%name%.json
@echo     }>>models/item/%name%.json
@echo }>>models/item/%name%.json