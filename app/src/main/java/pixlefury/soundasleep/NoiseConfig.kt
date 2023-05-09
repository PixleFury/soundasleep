package pixlefury.soundasleep

import noise.FastNoiseLite

enum class NoiseConfig(val buttonId: Int, val volume: Float, val noise: FastNoiseLite) {
	BROWN_NOISE(R.id.btn_brown, 0.75f, FastNoiseLite().apply {
        SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S)
        SetFrequency(0.01f)
        SetFractalType(FastNoiseLite.FractalType.FBm)
        SetFractalGain(0.5f)
        SetFractalLacunarity(2.0f)
        SetFractalOctaves(5)
        SetFractalPingPongStrength(2.0f)
    }),

	WHITE_NOISE(R.id.btn_white, 0.25f, FastNoiseLite().apply {
		SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2)
		SetFrequency(0.2f)
        SetFractalType(FastNoiseLite.FractalType.None)
	}),

    UNDERWATER(R.id.btn_underwater, 0.75f, FastNoiseLite().apply {
        SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S)
        SetFrequency(0.01f)
        SetFractalType(FastNoiseLite.FractalType.None)
        SetDomainWarpType(FastNoiseLite.DomainWarpType.BasicGrid)
        SetDomainWarpAmp(30.0f)
    }),
}