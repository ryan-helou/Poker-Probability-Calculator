/* eslint-disable react/no-unknown-property */
import { Canvas, useFrame, useThree } from "@react-three/fiber";
import { useRef, useMemo, useLayoutEffect } from "react";
import * as THREE from "three";

const hexToRGB = (hex) => {
  const h = hex.replace("#", "");
  return [
    parseInt(h.slice(0, 2), 16) / 255,
    parseInt(h.slice(2, 4), 16) / 255,
    parseInt(h.slice(4, 6), 16) / 255,
  ];
};

const vertexShader = `
varying vec2 vUv;
void main() {
  vUv = uv;
  gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
}
`;

const fragmentShader = `
varying vec2 vUv;
uniform float uTime;
uniform vec3 uColor;
uniform float uSpeed;
uniform float uScale;
uniform float uNoiseIntensity;

float noise(vec2 p) {
  return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
  vec2 uv = vUv * uScale;
  float t = uTime * uSpeed * 0.1;
  
  uv.y += 0.03 * sin(8.0 * uv.x - t);
  
  float pattern = 0.6 + 0.4 * sin(
    5.0 * (uv.x + uv.y + cos(3.0 * uv.x + 5.0 * uv.y) + 0.02 * t) +
    sin(20.0 * (uv.x + uv.y - 0.1 * t))
  );
  
  float n = noise(gl_FragCoord.xy) * uNoiseIntensity / 15.0;
  vec3 color = uColor * pattern - n;
  
  gl_FragColor = vec4(color, 1.0);
}
`;

function SilkPlane({ uniforms }) {
  const meshRef = useRef();
  const { viewport } = useThree();

  useLayoutEffect(() => {
    if (meshRef.current) {
      meshRef.current.scale.set(viewport.width, viewport.height, 1);
    }
  }, [viewport]);

  useFrame((state, delta) => {
    if (meshRef.current) {
      meshRef.current.material.uniforms.uTime.value += delta;
    }
  });

  return (
    <mesh ref={meshRef}>
      <planeGeometry args={[1, 1]} />
      <shaderMaterial
        uniforms={uniforms}
        vertexShader={vertexShader}
        fragmentShader={fragmentShader}
      />
    </mesh>
  );
}

export default function Silk({
  speed = 5,
  scale = 1,
  color = "#7B7481",
  noiseIntensity = 1.5,
}) {
  const uniforms = useMemo(
    () => ({
      uTime: { value: 0 },
      uSpeed: { value: speed },
      uScale: { value: scale },
      uNoiseIntensity: { value: noiseIntensity },
      uColor: { value: new THREE.Color(...hexToRGB(color)) },
    }),
    [speed, scale, noiseIntensity, color]
  );

  return (
    <Canvas dpr={[1, 2]} style={{ width: "100%", height: "100%" }}>
      <SilkPlane uniforms={uniforms} />
    </Canvas>
  );
}
